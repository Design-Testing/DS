
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import services.RegistrationService;
import services.SponsorshipService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Conference;
import domain.Registration;
import domain.Sponsorship;
import domain.Submission;
import forms.ConferenceForm;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private RegistrationService		registrationService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	// LIST --------------------------------------------------------

	@RequestMapping(value = "/myConferences", method = RequestMethod.GET)
	public ModelAndView myConferences() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findAll();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/myConferences.do");

		return result;
	}

	@RequestMapping(value = "/fiveDaysSubmission", method = RequestMethod.GET)
	public ModelAndView fiveDaysSubmission() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findFiveDaysFromSubmissionConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/fiveDaysSubmission.do");

		return result;
	}

	@RequestMapping(value = "/fiveDaysNotification", method = RequestMethod.GET)
	public ModelAndView fiveDaysNotification() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findFiveDaysForNotificationConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/fiveDaysNotification.do");

		return result;
	}

	@RequestMapping(value = "/fiveDaysCameraReady", method = RequestMethod.GET)
	public ModelAndView fiveDaysCameraReady() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findFiveDaysForCameraReadyConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/fiveDaysCameraReady.do");

		return result;
	}

	@RequestMapping(value = "/fiveDaysBeginning", method = RequestMethod.GET)
	public ModelAndView fiveDaysBeginning() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findFiveDaysForBeginningConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/fiveDaysBeginning.do");

		return result;
	}

	@RequestMapping(value = "/listFurthcoming", method = RequestMethod.GET)
	public ModelAndView listFurthcoming() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findFurthcomingConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/listFurthcoming.do");

		return result;
	}

	@RequestMapping(value = "/listPast", method = RequestMethod.GET)
	public ModelAndView listPast() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findPastConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "conference/administrator/listPast.do");

		return result;
	}

	@RequestMapping(value = "/listRunning", method = RequestMethod.GET)
	public ModelAndView listRunning() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findRunningConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requetURI", "conference/administrator/listRunning.do");

		return result;
	}

	// TO FINAL MODE --------------------------------------------------------

	@RequestMapping(value = "/finalMode", method = RequestMethod.GET)
	public ModelAndView finalMode(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();
		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (conference == null) {
			result = this.myConferences();
			result.addObject("msg", "error");
			result.addObject("isAdministrator", true);
		} else
			try {
				this.conferenceService.toFinalMode(conference);
				result = this.myConferences();
			} catch (final Throwable oops) {
				String errormsg = "error";
				result = this.myConferences();
				if (!conference.getIsDraft())
					errormsg = "conference.error.already.final";
				result.addObject("msg", errormsg);
				result.addObject("isAdministrator", true);
			}

		return result;
	}

	// CREATE  ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		final Conference conference = this.conferenceService.create();

		final ConferenceForm conferenceForm = this.conferenceService.constructPruned(conference);

		result = new ModelAndView("conference/edit");
		result.addObject("conferenceForm", conferenceForm); //this.constructPruned(position)
		result.addObject("isAdministrator", true);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("message", null);
		return result;
	}

	// EDIT --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (conference.getIsDraft()) {
			result = new ModelAndView("conference/edit");
			result.addObject("conferenceForm", this.conferenceService.constructPruned(conference)); //this.constructPruned(position)
			result.addObject("isAdministrator", true);
			result.addObject("categories", this.categoryService.findAll());
		}

		else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
	// SAVE --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("conferenceForm") @Valid final ConferenceForm conferenceForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("conference/edit");
			result.addObject("conferenceForm", conferenceForm);
			result.addObject("isAdministrator", true);
			result.addObject("categories", this.categoryService.findAll());
			result.addObject("errors", binding.getAllErrors());
		} else
			try {
				Conference conference = this.conferenceService.reconstruct(conferenceForm, binding);
				conference = this.conferenceService.save(conference);
				result = this.display(conference.getId());
			} catch (final ValidationException oops) {
				result = new ModelAndView("conference/edit");
				result.addObject("conferenceForm", conferenceForm);
				result.addObject("isAdministrator", true);
				result.addObject("categories", this.categoryService.findAll());
				result.addObject("errors", "commit.error");
			} catch (final Throwable oops) {
				result = new ModelAndView("conference/edit");
				result.addObject("conferenceForm", conferenceForm);
				result.addObject("isAdministrator", true);
				result.addObject("categories", this.categoryService.findAll());
				if (oops.getMessage().equals("no deadline or date can be null"))
					result.addObject("msgerror", "conference.error.empty");
				if (oops.getMessage().equals("submission before notification"))
					result.addObject("msgerror", "conference.submission.before.notification");
				if (oops.getMessage().equals("notification before camera ready"))
					result.addObject("msgerror", "conference.notification.before.camera.ready");
				if (oops.getMessage().equals("camera ready before start date"))
					result.addObject("msgerror", "conference.camera.ready.before.start.date");
				if (oops.getMessage().equals("start date before end date"))
					result.addObject("msgerror", "conference.start.date.before.end.date");
				result.addObject("errors", binding.getAllErrors());

			}

		return result;
	}

	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		final Conference conference = this.conferenceService.findOne(conferenceId);

		final Collection<Submission> submissions = this.submissionService.findSubmissionsByConference(conferenceId);
		final Collection<Submission> acceptedSubmissions = this.submissionService.findAcceptedSubmissionsByConference(conferenceId);
		final Collection<Submission> rejectedSubmissions = this.submissionService.findRejectedSubmissionsByConference(conferenceId);
		final Collection<Submission> underReviewedSubmissions = this.submissionService.findUnderReviewedSubmissionsByConference(conferenceId);

		if (conference != null) {

			Boolean isNotificationElapsed = false;
			Boolean isSubmissionElapsed = false;

			final Date now = new Date();
			if (now.after(conference.getNotification()))
				isNotificationElapsed = true;
			if (now.after(conference.getSubmission()))
				isSubmissionElapsed = true;

			final Collection<Registration> registrations = this.registrationService.findByConference(conferenceId);
			Boolean hasRegistrations = false;
			if (!registrations.isEmpty())
				hasRegistrations = true;

			String imgBanner = null;
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				imgBanner = sponsorship.getBanner();

			result = new ModelAndView("conference/display");
			result.addObject("conference", conference);
			result.addObject("isAdministrator", true);
			result.addObject("submissions", submissions);
			result.addObject("imgBanner", imgBanner);
			result.addObject("hasRegistrations", hasRegistrations);
			result.addObject("isNotificationElapsed", isNotificationElapsed);
			result.addObject("isSubmissionElapsed", isSubmissionElapsed);
			result.addObject("acceptedSubmissions", acceptedSubmissions);
			result.addObject("rejectedSubmissions", rejectedSubmissions);
			result.addObject("underReviewedSubmissions", underReviewedSubmissions);
			result.addObject("ratioDecidedAssignedSubmissions", this.submissionService.getRatioCalculatedSubmissionsOverAssignedSubmissions(conferenceId));

		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
	// DECIDE ON CONFERENCE --------------------------------------------------------

	@RequestMapping(value = "/decideOnConference", method = RequestMethod.GET)
	public ModelAndView decideOnConference(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (conference != null)
			try {
				this.conferenceService.decideOnConference(conferenceId);
				result = this.display(conferenceId);
				result.addObject("notificationMsgSuccess", "message.make.desicion.successful");

			} catch (final Throwable oops) {
				result = this.display(conferenceId);
				if (oops.getMessage().equals("submission deadline must be elapsed"))
					result.addObject("notificationMsg", "conference.submission.not.elapsed");
				if (oops.getMessage().equals("no reports decided"))
					result.addObject("notificationMsg", "conference.no.status.updated");
				if (oops.getMessage().equals("All the submissions of this conference has already been decided"))
					result.addObject("notificationMsg", "message.all.submissions.already.decided");
				else
					result.addObject("notificationMsgDecision", oops.getMessage());

			}
		else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}

	@RequestMapping(value = "/notifyStatus", method = RequestMethod.GET)
	public ModelAndView notifyStatus(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (conference != null)
			try {
				this.conferenceService.notifyStatus(conferenceId);
				result = this.display(conferenceId);
				result.addObject("notificationMsgSuccess", "message.notification.successful");
			} catch (final Throwable oops) {
				result = this.display(conferenceId);
				if (oops.getMessage().equals("notification deadline is elapsed"))
					result.addObject("notificationMsg", "conference.notification.elapsed");
				if (oops.getMessage().equals("submission deadline must be elapsed"))
					result.addObject("notificationMsg", "conference.submission.not.elapsed");
				//else
				//result.addObject("notificationMsg", "commit.error");

			}
		else
			result = new ModelAndView("redirect:misc/403");

		return result;

	}

	/** debe estar en el display de conference arriba de todas las submissions **/
	@RequestMapping(value = "/runAssignation", method = RequestMethod.GET)
	public ModelAndView runAssignation(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		try {

			this.conferenceService.runReviewerAssignation(conferenceId);
			result = this.display(conferenceId);
			result.addObject("notificationMsgSuccess", "message.run.assignation.success");

		} catch (final Throwable oops) {
			result = this.display(conferenceId);
			if (oops.getMessage().equals("all submissions are already been decided"))
				result.addObject("notificationMsg", "conference.all.submissions.decided");
			if (oops.getMessage().equals("no reviewers available to be assigned"))
				result.addObject("notificationMsg", "conference.no.reviewers.available");
			if (oops.getMessage().equals("the notification deadline has elapsed"))
				result.addObject("notificationMsg", "conference.notification.elapsed");
		}
		return result;
	}

}
