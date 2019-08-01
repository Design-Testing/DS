
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

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


	public Submission create(final int conferenceId) {
		final Submission res = new Submission();
		final String ticker = this.generateTicker();
		final Author principal = this.authorService.findByPrincipal();
		res.setAuthor(principal);
		res.setTicker(ticker);
		res.setMoment(new Date());
		res.setStatus("UNDER-REVIEWED");

		final Conference conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(!conference.getIsDraft(), "conference must be in final mode");
		res.setConference(conference);

		return res;
	}

	/*
	 * public Submission save(final Submission submission, final Conference conference, final Author author, final Paper cameraReadyPaper, final Paper reviewPaper) {
	 * Assert.notNull(submission);
	 * Assert.notNull(conference);
	 * Assert.notNull(author);
	 * Assert.notNull(cameraReadyPaper);
	 * Assert.notNull(reviewPaper);
	 * 
	 * Assert.isTrue(Pattern.matches("yyyy-MM-dd HH:mm", submission.getMoment().toString()));
	 * Assert.isTrue(Pattern.matches("^([A-Z]{3}-[0-9A-Z]{4})$", submission.getTicker()));
	 * Assert.isTrue(Pattern.matches("^(UNDER-REVIEWED|ACCEPTED|REJECTED)$", submission.getStatus()));
	 * 
	 * submission.setConference(conference);
	 * submission.setAuthor(author);
	 * submission.setCameraReadyPaper(cameraReadyPaper);
	 * submission.setReviewPaper(reviewPaper);
	 * 
	 * final Submission res = this.submissionRepository.save(submission);
	 * Assert.notNull(res);
	 * 
	 * return res;
	 * }
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
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

		final Paper paperSaved = this.paperService.save(reviewPaper);
		submission.setReviewPaper(paperSaved);
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
		final Date now = new Date();
		Assert.isTrue(now.before(retrieved.getConference().getCameraReady()), "camera-ready deadline is elapsed");
		retrieved.setCameraReadyPaper(cameraReadyPaper);
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
		final Submission submission = this.findOne(submissionId);
		final Reviewer reviewer = this.reviewerService.findOne(reviewerId);
		Assert.notNull(submission);
		Assert.notNull(reviewer);
		Assert.isTrue(submission.getStatus().equals("UNDER-REVIEWED"));
		final Collection<Reviewer> reviewers = submission.getReviewers();
		Assert.isTrue(!reviewers.contains(reviewer), "this reviewer has already been assigned to that submission");
		Assert.isTrue(reviewers.size() <= 3, "no more than 3 reviewers can be assigned to a submission");
		reviewers.add(reviewer);
		submission.setReviewers(reviewers);
		this.submissionRepository.save(submission);
	}

	/**
	 * Run a decision-making procedure on a conference,
	 * as long as the corresponding submission deadline has elapsed.
	 * Making a decision on a submission means analysing the reports
	 * written by the reviewers to decide if the corresponding submission
	 * must change its status to either REJECTED or ACCEPTED.
	 * A submission is accepted if the number of reports with decision
	 * ACCEPT that it's got is greater than or equal to the number
	 * of reports with decision REJECT that it's got;
	 * in cases of ties, reports whose decision is BORDER-LINE
	 * are considered to ACCEPT the paper; in cases in which ties persist,
	 * then the corresponding submissions are accepted.
	 **/

	//TODO ALBA REVISAR
	public void decideOnSubmission(final int submissionId) {
		this.administratorService.findByPrincipal();
		final Submission retrieved = this.findOne(submissionId);
		Assert.notNull(retrieved);
		Integer numberAccept = 0;
		Integer numberReject = 0;
		Integer numberBorderLine = 0;
		final Collection<Report> reports = this.reportService.findReportsBySubmission(submissionId);
		for (final Report r : reports)
			if (r.getDecision().equals("ACCEPT"))
				numberAccept = numberAccept + 1;
			else if (r.getDecision().equals("REJECT"))
				numberReject = numberReject + 1;
			else if (r.getDecision().equals("BORDER-LINE"))
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

	/*
	 * public void delete(final Integer submissionId) {
	 * Assert.notNull(submissionId);
	 * this.submissionRepository.delete(submissionId);
	 * }
	 */

	/** El ticker es ABC-XXXX donde ABC son las inciales del autor y XXXX cuatro letras randoms en mayuscula **/
	public String generateTicker() {
		String res = "";
		final String abecedary = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Integer n1 = (int) Math.floor(Math.random() * 25 + 1);
		final Integer n2 = (int) Math.floor(Math.random() * 25 + 1);
		final Integer n3 = (int) Math.floor(Math.random() * 25 + 1);
		final Integer n4 = (int) Math.floor(Math.random() * 25 + 1);

		final Author author = this.authorService.findByPrincipal();

		final Character initial1 = author.getName().charAt(0);
		Character initial2 = null;
		if (author.getMiddleName() != null && author.getMiddleName() != "")
			initial2 = author.getMiddleName().charAt(0);
		else
			initial2 = 'X';

		final Character initial3 = author.getSurname().get(0).charAt(0);

		res = "" + initial1 + "" + initial2 + "" + initial3 + "-" + abecedary.charAt(n1) + abecedary.charAt(n2) + abecedary.charAt(n3) + abecedary.charAt(n4);

		final Collection<Submission> submissions = this.submissionRepository.getSubmissionWithTicker(res);
		if (!submissions.isEmpty())
			res = this.generateTicker();
		return res;
	}
	public Collection<Reviewer> availableReviewers(final int submissionId) {
		final Collection<Reviewer> reviewers = this.reviewerService.findAll();
		final Submission submission = this.findOne(submissionId);
		Assert.notNull(submission);
		final Collection<Reviewer> reviewersAssigned = submission.getReviewers();
		reviewers.removeAll(reviewersAssigned);
		return reviewers;
	}

	public void runAssignation() {
		// TODO Auto-generated method stub

	}

}
