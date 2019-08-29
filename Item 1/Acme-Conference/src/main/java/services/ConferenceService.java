
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import repositories.SubmissionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Activity;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Conference;
import domain.Message;
import domain.Report;
import domain.Reviewer;
import domain.Submission;
import domain.Topic;
import forms.ConferenceForm;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository	conferenceRepository;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private TopicService			topicService;

	@Autowired
	private ReviewerService			reviewerService;

	//	@Autowired
	//	private ActivityService			activityService;

	@Autowired
	private Validator				validator;


	//METODOS CRUD
	public Conference create() {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - create");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - create");

		final Collection<Category> categories = this.categoryService.findCategoriesWithNameConference();
		final Category category = categories.iterator().next();

		final Conference res = new Conference();
		res.setTitle("");
		res.setAcronym("");
		res.setVenue("");
		res.setSubmission(null);
		res.setNotification(null);
		res.setCameraReady(null);
		res.setStartDate(null);
		res.setEndDate(null);
		res.setCategory(category);
		res.setSummary("");
		res.setFee(0.0);
		res.setIsDraft(true);

		return res;
	}

	public Conference findOne(final int idConference) {
		Assert.isTrue(idConference != 0, "El idConference debe ser distinto de 0");
		final Conference res = this.conferenceRepository.findOne(idConference);
		Assert.notNull(res);
		return res;
	}

	public Collection<Conference> findAll() {
		final Collection<Conference> res = this.conferenceRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Conference save(final Conference conference) {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - save");

		if (conference.getId() != 0) {
			final Conference retrieved = this.findOne(conference.getId());
			Assert.isTrue(retrieved.getIsDraft().equals(true), "The conference must be in draft mode");
		}

		conference.setIsDraft(true);

		Assert.isTrue(conference.getSubmission() != null && conference.getNotification() != null && conference.getCameraReady() != null && conference.getStartDate() != null && conference.getEndDate() != null, "no deadline or date can be null");

		Assert.isTrue(conference.getSubmission().before(conference.getNotification()), "submission before notification");
		Assert.isTrue(conference.getNotification().before(conference.getCameraReady()), "notification before camera ready");
		Assert.isTrue(conference.getCameraReady().before(conference.getStartDate()), "camera ready before start date");
		Assert.isTrue(conference.getStartDate().before(conference.getEndDate()), "start date before end date");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - save");

		Assert.notNull(conference.getCategory(), "The category can't be null");
		final Conference res = this.conferenceRepository.save(conference);
		return res;
	}

	//TODO ALBA ** hacer que no se pueda pasar a final hasta que tenga alguna actividad?
	public void toFinalMode(final Conference conference) {
		final Conference retrieved = this.findOne(conference.getId());
		Assert.notNull(retrieved, "the conference does not exist");
		Assert.isTrue(retrieved.getIsDraft().equals(true), "The conference must be in draft mode");
		conference.setIsDraft(false);
		this.conferenceRepository.save(conference);
	}

	//OTRO METODOS
	/**
	 * Method designed for the correct operation of the finder
	 * 
	 * @param keyword
	 *            Single keyword that must appear somewhere in its title, its venue, or its summary.
	 * @param categoryName
	 *            A category to which the conference must belong
	 * @param fromDate
	 *            A range of dates for the conference
	 * @param toDate
	 *            A range of dates for the conference
	 * @param maximumFee
	 *            A maximum fee for the conference
	 * @return Collection of conferences that meet the restrictions given as parameters
	 * @author a8081
	 * */
	public Collection<Conference> findConferences(final String keyword, final String categoryName, final Date fromDate, final Date toDate, final Double maximumFee) {
		final Collection<Conference> res = this.conferenceRepository.findConferences(keyword, categoryName, fromDate, toDate, maximumFee);
		Assert.notNull(res);
		return res;
	}

	/**
	 * Method designed for the correct operation of the searching
	 * 
	 * @param keyword
	 *            Single keyword that must appear somewhere in its title, its venue, or its summary.
	 * @return Collection of conferences that meet the restrictions given as keyword
	 * @author a8081
	 * */
	public Collection<Conference> findConferences(final String keyword) {
		final Collection<Conference> res = this.conferenceRepository.findConferences(keyword);
		Assert.notNull(res);
		return res;
	}

	public Collection<Conference> findFurthcomingConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFurthcomingConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findPastConferences() {
		final Collection<Conference> result = this.conferenceRepository.findPastConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findRunningConferences() {
		final Collection<Conference> result = this.conferenceRepository.findRunningConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFiveDaysFromSubmissionConferences() {

		final Collection<Conference> result = this.conferenceRepository.findFiveDaysFromSubmissionConferences();
		Assert.notNull(result);
		return result;

	}

	public Collection<Conference> findFiveDaysForNotificationConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFiveDaysForNotificationConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFiveDaysForCameraReadyConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFiveDaysForCameraReadyConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFiveDaysForBeginningConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFiveDaysForBeginningConferences();
		Assert.notNull(result);
		return result;
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

	public Boolean decideOnConference(final int conferenceId) {
		Boolean anyStatusUpdated = false;
		this.administratorService.findByPrincipal();
		final Conference retrieved = this.findOne(conferenceId);
		Assert.notNull(retrieved);
		//final Date now = new Date();
		//TODO submission deadline
		//Assert.isTrue(retrieved.getSubmission().before(now), "submission deadline must be elapsed");
		final Collection<Submission> submissions = this.submissionService.findUnderReviewedSubmissionsByConference(conferenceId);

		if (!submissions.isEmpty())
			anyStatusUpdated = true;

		for (final Submission s : submissions)
			this.submissionService.decideOnSubmission(s.getId());

		return anyStatusUpdated;
	}
	public ConferenceForm constructPruned(final Conference conference) {
		final ConferenceForm pruned = new ConferenceForm();

		pruned.setId(conference.getId());
		pruned.setVersion(conference.getVersion());
		pruned.setTitle(conference.getTitle());
		pruned.setAcronym(conference.getAcronym());
		pruned.setVenue(conference.getVenue());
		pruned.setSubmission(conference.getSubmission());
		pruned.setNotification(conference.getNotification());
		pruned.setCameraReady(conference.getCameraReady());
		pruned.setStartDate(conference.getStartDate());
		pruned.setEndDate(conference.getEndDate());
		pruned.setSummary(conference.getSummary());
		pruned.setFee(conference.getFee());
		pruned.setCategory(conference.getCategory());

		return pruned;
	}

	public Conference reconstruct(final ConferenceForm conferenceForm, final BindingResult binding) {
		Conference result;

		if (conferenceForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(conferenceForm.getId());

		result.setVersion(conferenceForm.getVersion());
		result.setTitle(conferenceForm.getTitle());
		result.setAcronym(conferenceForm.getAcronym());
		result.setVenue(conferenceForm.getVenue());
		result.setSubmission(conferenceForm.getSubmission());
		result.setNotification(conferenceForm.getNotification());
		result.setCameraReady(conferenceForm.getCameraReady());
		result.setStartDate(conferenceForm.getStartDate());
		result.setEndDate(conferenceForm.getEndDate());
		result.setSummary(conferenceForm.getSummary());
		result.setFee(conferenceForm.getFee());
		result.setCategory(conferenceForm.getCategory());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	Conference findConference(final int activityId) {
		return this.conferenceRepository.findConference(activityId);
	}

	Collection<Activity> findConferenceActivities(final int conferenceId) {
		return this.conferenceRepository.findConferenceActivities(conferenceId);
	}

	//	public void delete(final Conference conference) {
	//		Assert.notNull(conference);
	//		Assert.isTrue(conference.getId() != 0);
	//		this.administratorService.findByPrincipal();
	//		Assert.isTrue(this.conferenceRepository.findOne(conference.getId()).equals(conference), "No se puede borrar una actividad que no existe");
	//		this.conferenceRepository.delete(conference);
	//		this.activityService.deleteAll(conference.getActivities());
	//	}

	/** Notify the status of the submissions to all its respective authors **/
	public void notifyStatus(final int conferenceId) {

		final Conference conference = this.findOne(conferenceId);
		Assert.notNull(conference);
		final Administrator principal = this.administratorService.findByPrincipal();
		final Date now = new Date();
		Assert.isTrue(now.before(conference.getNotification()), "notification deadline is elapsed");
		//TODO submission deadline
		//Assert.isTrue(now.after(conference.getSubmission()), "submission deadline must be elapsed");
		final Collection<Submission> submissions = this.submissionService.findSubmissionsByConference(conferenceId);

		final Collection<Topic> topics = this.topicService.findTopicByNames("OTRO", "OTHER");
		final Topic topic = topics.iterator().next();

		for (final Submission s : submissions) {

			final Message m = this.messageService.create();
			m.setSender(principal);
			final Collection<Actor> recipients = new ArrayList<Actor>();
			recipients.add(s.getAuthor());
			m.setRecivers(recipients);
			m.setTopic(topic);

			if (!s.getStatus().equals("UNDER-REVIEWED")) {

				m.setSubject("Your submission has been " + s.getStatus());
				m.setBody("Your submission with ticker" + s.getTicker() + "(to the conference with title " + conference.getTitle() + ") has been " + s.getStatus());

				s.setIsNotified(true);
				this.submissionRepository.save(s);

			} else {
				m.setSubject("Your submission is still under reviewed");
				m.setBody("Your submission with ticker" + s.getTicker() + "(to the conference with title " + conference.getTitle()
					+ ") is still under reviewed. There may no be available reviewers. Please, wait until the notification deadline is elapsed. Sorry for the incovenience.");
			}

			this.messageService.send(m);

		}
	}

	public Collection<Conference> findAllFinal() {
		final Collection<Conference> result = this.conferenceRepository.findAllFinal();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFinalFurthcomingConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFinalFurthcomingConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFinalPastConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFinalPastConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFinalRunningConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFinalRunningConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findAllByAuthorUserId(final int authorUAId) {
		final Collection<Conference> result = this.conferenceRepository.findAllByAuthorUserId(authorUAId);
		Assert.notNull(result);
		return result;
	}

	public Conference findConferenceByTutorialId(final int tutorialId) {
		final Conference result = this.conferenceRepository.findConferenceByTutorialId(tutorialId);
		Assert.notNull(result);
		return result;
	}

	//	public Collection<Conference> conferenceAvailable(final int authorUAId) {
	//		final Collection<Conference> hwConference = this.findAllByAuthorUserId(authorUAId);
	//		final Collection<Conference> conference = this.findAll();
	//		conference.removeAll(hwConference);
	//		return conference;
	//	}

	public Collection<Conference> conferenceAvailable(final int authorId) {
		Assert.isTrue(authorId != 0);
		final Collection<Conference> res = this.conferenceRepository.findConferenceAvailableToRegistration(authorId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Conference> findConferenceWithRegistration(final int authorId) {
		Assert.isTrue(authorId != 0);
		final Collection<Conference> res = this.conferenceRepository.findConferenceWithRegistration(authorId);
		Assert.notNull(res);
		return res;
	}

	public boolean exists(final int id) {
		return this.conferenceRepository.exists(id);
	}

	public void runReviewerAssignation(final int conferenceId) {

		final Administrator principal = this.administratorService.findByPrincipal();
		final Conference conference = this.findOne(conferenceId);
		Assert.notNull(conference);
		final Date now = new Date();
		Assert.isTrue(now.before(conference.getNotification()), "the notification deadline has elapsed");

		final Collection<Submission> submissionsToAssign = this.submissionService.findUnderReviewedSubmissionsByConference(conferenceId);

		Assert.isTrue(!submissionsToAssign.isEmpty(), "all submissions are already been decided");
		final Collection<Reviewer> reviewers = this.reviewerService.findReviewersAccordingToConference(conferenceId);
		Assert.isTrue(!reviewers.isEmpty(), "no reviewers available to be assigned");

		final Collection<Topic> topics = this.topicService.findTopicByNames("OTRO", "OTHER");
		final Topic topic = topics.iterator().next();

		for (final Submission s : submissionsToAssign)
			if (this.reportService.findReportsBySubmission(s.getId()).size() < 3)
				for (final Reviewer r : reviewers) {
					final Report existingReport = this.reportService.findReportBySubmissionAndReviewer(s.getId(), r.getId());
					if (existingReport == null && this.reportService.findReportsBySubmission(s.getId()).size() < 3) {
						Report newReport = this.reportService.create(s.getId(), r.getId());
						newReport = this.reportService.save(newReport, s, r);
						final Message m = this.messageService.create();
						m.setSubject("You has been assigned a new submission");
						m.setBody("You has been assigned to the submission with ticker " + s.getTicker());
						m.setSender(principal);
						final Collection<Actor> recipients = new ArrayList<Actor>();
						recipients.add(r);
						m.setRecivers(recipients);
						m.setTopic(topic);
						this.messageService.send(m);
					}
				}

	}

	public Collection<Conference> reassignConferences(final int categoryId) {
		final List<Conference> toReassign = new ArrayList<Conference>(this.conferenceRepository.findByCategory(categoryId));
		final Category father = this.categoryService.findOne(categoryId).getFather();
		System.out.println("Reasignacion a la categoria padre: " + father.getTitleEs());
		System.out.println("Se van a reasignar las conferencias: " + toReassign);
		for (int i = 0; i < toReassign.size(); i++) {
			final Conference c = toReassign.get(i);
			c.setCategory(father);
			System.out.println("Conferencia reasignada: " + c.getTitle() + "/ " + c.getCategory());
			this.conferenceRepository.save(c);
		}

		return toReassign;

	}

}
