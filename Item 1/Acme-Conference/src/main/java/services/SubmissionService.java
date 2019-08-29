
package services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Conference;
import domain.Message;
import domain.Paper;
import domain.Report;
import domain.Reviewer;
import domain.Submission;
import domain.Topic;

@Service
//@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private PaperService			paperService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private TopicService			topicService;


	public Submission create(final int conferenceId) {
		final Submission res = new Submission();
		final String ticker = this.generateTicker();
		final Author principal = this.authorService.findByPrincipal();
		res.setAuthor(principal);
		res.setTicker(ticker);
		res.setMoment(new Date());
		res.setStatus("UNDER-REVIEWED");
		res.setIsNotified(false);
		final Conference conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(!conference.getIsDraft(), "conference must be in final mode");
		res.setConference(conference);

		return res;
	}

	/** Solo se llama en el conference service para setearle el atribtuo isNotified **/
	public Submission save(final Submission submission) {
		Assert.notNull(submission);

		final Submission res = this.submissionRepository.save(submission);
		Assert.notNull(res);

		return res;
	}

	public Submission submits(final Submission submission, final Paper reviewPaper) {
		Submission res;
		final Author principal = this.authorService.findByPrincipal();
		submission.setAuthor(principal);
		Assert.isTrue(submission.getId() == 0);
		Assert.notNull(submission.getConference() != null);
		Assert.isTrue(!submission.getConference().getIsDraft(), "conferece must be in final mode");
		Assert.isTrue(submission.getStatus().equals("UNDER-REVIEWED"));
		final Date now = new Date();
		Assert.isTrue(now.before(submission.getConference().getSubmission()), "submission deadline is elapsed");
		submission.setMoment(new Date(System.currentTimeMillis() - 1000));
		submission.setIsNotified(false);
		final Paper paperSaved = this.paperService.save(reviewPaper);
		submission.setReviewPaper(paperSaved);
		System.out.println(submission.getTicker());
		res = this.submissionRepository.save(submission);
		Assert.notNull(res);
		return res;

	}
	public void sendCameraReadyPaper(final int submissionId, final Paper cameraReadyPaper) {
		final Author principal = this.authorService.findByPrincipal();
		final Submission retrieved = this.findOne(submissionId);
		Assert.notNull(retrieved);
		Assert.isTrue(retrieved.getAuthor().equals(principal));
		Assert.isTrue(retrieved.getStatus().equals("ACCEPTED"));
		if (cameraReadyPaper.getId() != 0)
			Assert.isTrue(retrieved.getCameraReadyPaper().equals(cameraReadyPaper) && retrieved.getAuthor().equals(principal));
		else
			Assert.isTrue(retrieved.getAuthor().equals(principal));
		final Date now = new Date();
		Assert.isTrue(now.before(retrieved.getConference().getCameraReady()), "camera-ready deadline is elapsed");
		final Paper paperSaved = this.paperService.save(cameraReadyPaper);
		retrieved.setCameraReadyPaper(paperSaved);
		this.submissionRepository.save(retrieved);

	}

	public void acceptSubmission(final int submissionId) {
		this.administratorService.findByPrincipal();
		final Submission retrieved = this.findOne(submissionId);
		Assert.notNull(retrieved);
		Assert.isTrue(retrieved.getStatus().equals("UNDER-REVIEWED"));
		retrieved.setStatus("ACCEPTED");
		this.submissionRepository.save(retrieved);
	}

	public void rejectSubmission(final int submissionId) {
		this.administratorService.findByPrincipal();
		final Submission retrieved = this.findOne(submissionId);
		Assert.notNull(retrieved);
		Assert.isTrue(retrieved.getStatus().equals("UNDER-REVIEWED"));
		retrieved.setStatus("REJECTED");
		this.submissionRepository.save(retrieved);
	}

	public void assignToReviewer(final int submissionId, final int reviewerId) {
		final Administrator principal = this.administratorService.findByPrincipal();
		final Submission submission = this.findOne(submissionId);
		final Reviewer reviewer = this.reviewerService.findOne(reviewerId);
		Assert.notNull(submission);
		Assert.notNull(reviewer);
		final Date now = new Date();
		Assert.isTrue(now.before(submission.getConference().getNotification()), "notification deadline is elapsed");
		Assert.isTrue(submission.getStatus().equals("UNDER-REVIEWED"), "submission status must be under-reviwed to be assigned to a reviewer");
		final Report existingReport = this.reportService.findReportBySubmissionAndReviewer(submissionId, reviewerId);
		Assert.isTrue(existingReport == null, "this reviewer has already been assigned to that submission");
		final Collection<Reviewer> reviewers = this.reviewerService.findReviewersAccordingToConference(submission.getConference().getId());
		Assert.isTrue(reviewers.contains(reviewer), "keywords of the reviewer must be contained within the title or the summary of the conference");
		final Report newReport = this.reportService.create(submissionId, reviewerId);
		this.reportService.save(newReport, submission, reviewer);

		final Collection<Topic> topics = this.topicService.findTopicByNames("OTRO", "OTHER");
		final Topic topic = topics.iterator().next();
		final Message m = this.messageService.create();
		m.setSubject("You has been assigned a new submission");
		m.setBody("You has been assigned to the submission with ticker " + submission.getTicker());
		m.setSender(principal);
		final Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(reviewer);
		m.setRecivers(recipients);
		m.setTopic(topic);
		this.messageService.send(m);

	}

	//TODO pasar a queries
	/** Calcula resultado de una submission. Este metodo es llamado desde decideOnConference en ConferenceService **/
	public void decideOnSubmission(final int submissionId) {
		this.administratorService.findByPrincipal();
		final Submission retrieved = this.findOne(submissionId);
		Assert.notNull(retrieved);
		Integer numberAccept = 0;
		Integer numberReject = 0;
		Integer numberBorderLine = 0;
		final Collection<Report> reports = this.reportService.findReportsBySubmission(submissionId);
		if (!reports.isEmpty()) {
			for (final Report r : reports)
				if (r.getDecision().equals("ACCEPT") && r.getIsDraft() == false)
					numberAccept = numberAccept + 1;
				else if (r.getDecision().equals("REJECT") && r.getIsDraft() == false)
					numberReject = numberReject + 1;
				else if (r.getDecision().equals("BORDER-LINE") && r.getIsDraft() == false)
					numberBorderLine = numberBorderLine + 1;
			if (numberAccept > numberReject)
				this.acceptSubmission(submissionId);
			else if (numberAccept == numberReject) {
				if (numberBorderLine > 0)
					this.acceptSubmission(submissionId);
				else
					this.acceptSubmission(submissionId);
			} else if (numberReject > numberAccept)
				this.rejectSubmission(submissionId);
		}
	}

	public Submission findOne(final Integer submissionId) {
		Assert.notNull(submissionId);
		final Submission res = this.submissionRepository.findOne(submissionId);
		Assert.notNull(res);

		return res;
	}

	public Collection<Submission> findAll() {
		final Collection<Submission> res = this.submissionRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Collection<Submission> findAcceptedSubmissionsByConference(final int conferenceId) {
		final Collection<Submission> res = this.submissionRepository.findAcceptedSubmissionsByConference(conferenceId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Submission> findRejectedSubmissionsByConference(final int conferenceId) {
		final Collection<Submission> res = this.submissionRepository.findRejectedSubmissionsByConference(conferenceId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Submission> findUnderReviewedSubmissionsByConference(final int conferenceId) {
		final Collection<Submission> res = this.submissionRepository.findUnderReviewedSubmissionsByConference(conferenceId);
		Assert.notNull(res);
		return res;
	}

	public List<Submission> getSubmissionsByAuthor(final int authorId) {
		final List<Submission> res = this.submissionRepository.getSubmissionsByAuthor(authorId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Submission> findSubmissionsByConference(final int conferenceId) {
		final Collection<Submission> result = this.submissionRepository.findSubmissionsByConference(conferenceId);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Integer submissionId) {
		Assert.notNull(submissionId);
		this.submissionRepository.delete(submissionId);
	}

	/** El ticker es ABC-XXXX donde ABC son las inciales del autor y XXXX cuatro letras randoms en mayuscula **/
	public String generateTicker() {
		String res = "";

		final String abecedary = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String numbers = "0123456789";
		final SecureRandom random = new SecureRandom();

		final String data = abecedary + numbers;
		final StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {

			// 0-62 (exclusive), random returns 0-61
			final int rndCharAt = random.nextInt(data.length());
			final char rndChar = data.charAt(rndCharAt);

			// debug
			//System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);

			sb.append(rndChar);

		}

		final String randomString = sb.toString();

		final Author author = this.authorService.findByPrincipal();

		Character initial1 = author.getName().charAt(0);
		Character initial2 = null;
		if (author.getMiddleName() != null && author.getMiddleName() != "")
			initial2 = author.getMiddleName().charAt(0);
		else
			initial2 = 'X';

		Character initial3 = author.getSurname().charAt(0);

		initial1 = Character.toUpperCase(initial1);
		initial2 = Character.toUpperCase(initial2);
		initial3 = Character.toUpperCase(initial3);

		res = "" + initial1 + "" + initial2 + "" + initial3 + "-" + randomString;

		final Collection<Submission> submissions = this.submissionRepository.getSubmissionWithTicker(res);
		if (!submissions.isEmpty())
			res = this.generateTicker();
		return res;
	}

	//	public void runReviewerAssignation() {
	//		this.administratorService.findByPrincipal();
	//		final Collection<Submission> submissionsToAssign = this.findUnderReviewedSubmissions();
	//		//Assert.isTrue(!submissionsToAssign.isEmpty(), "all submissions are already been decided");
	//		for (final Submission s : submissionsToAssign)
	//			if (this.reportService.findReportsBySubmission(s.getId()).size() < 3) {
	//				final Conference conference = s.getConference();
	//				final Collection<Reviewer> reviewers = this.reviewerService.findReviewersAccordingToConference(conference.getId());
	//				for (final Reviewer r : reviewers) {
	//					final Report existingReport = this.reportService.findReportBySubmissionAndReviewer(s.getId(), r.getId());
	//					if (existingReport == null && this.reportService.findReportsBySubmission(s.getId()).size() < 3) {
	//						Report newReport = this.reportService.create(s.getId(), r.getId());
	//						newReport = this.reportService.save(newReport, s, r);
	//					}
	//				}
	//
	//			}
	//
	//	}

	public Collection<Submission> findUnderReviewedSubmissions() {
		final Collection<Submission> result = this.submissionRepository.findUnderReviewedSubmissions();
		Assert.notNull(result);
		return result;
	}

	public Collection<Reviewer> availableReviewers(final int submissionId) {
		final Submission s = this.findOne(submissionId);
		final Conference conference = s.getConference();
		final Collection<Reviewer> reviewers = this.reviewerService.findReviewersAccordingToConference(conference.getId());
		final Collection<Reviewer> result = new ArrayList<Reviewer>();
		for (final Reviewer r : reviewers) {
			final Report existingReport = this.reportService.findReportBySubmissionAndReviewer(s.getId(), r.getId());
			if (existingReport == null)
				result.add(r);
		}
		return result;
	}

	/**
	 * If returns 1.0, then all the asigned submissions of the specified conference have its status calculated
	 * If returns a number between [0.0,1.0), then there are assigned submissions that are not evaluated
	 **/
	public Double getRatioCalculatedSubmissionsOverAssignedSubmissions(final int conferenceId) {
		Double result = this.submissionRepository.getRatioCalculatedSubmissionsOverAssignedSubmissions(conferenceId);
		if (result == null)
			result = -1.0;
		return result;
	}
}
