
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService				administratorService;

	@Autowired
	private SubmissionService					submissionService;

	@Autowired
	private ReviewerService						reviewerService;

	@Autowired
	private ConferenceAdministratorController	conferenceAdministratorController;


	// LIST --------------------------------------------------------

	@RequestMapping(value = "/submissions", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Submission> submissions = this.submissionService.findAll();

		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("isAdministrator", true);
		result.addObject("isAuthor", false);
		result.addObject("requestURI", "submission/administrator/submissions.do");

		return result;
	}

	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int submissionId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		this.administratorService.findByPrincipal();

		final Submission submission = this.submissionService.findOne(submissionId);

		if (submission != null) {
			result = new ModelAndView("submission/display");
			result.addObject("submission", submission);
			result.addObject("isAdministrator", true);
			result.addObject("isAuthor", false);
			result.addObject("lang", lang);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}

	// ASSIGN TO REVIEWER --------------------------------------------------------

	/**
	 * debe estar dentro del display de conference, al lado de cada submission
	 * OJO: solo en las submissions que estan en esatdo UNDER-REVIEW y además tiene
	 * que tener menos de 3 reviewers asignados
	 **/
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int submissionId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		this.administratorService.findByPrincipal();

		final Submission submission = this.submissionService.findOne(submissionId);

		final Collection<Reviewer> reviewers = this.submissionService.availableReviewers(submissionId);

		Boolean notificationElapsed = false;

		final Date now = new Date();
		if (now.after(submission.getConference().getNotification()))
			notificationElapsed = true;

		if (submission != null && notificationElapsed == false) {
			result = new ModelAndView("submission/listReviewers");
			result.addObject("submissionId", submissionId);
			result.addObject("reviewers", reviewers);
			result.addObject("isAdministrator", true);
			result.addObject("isAuthor", false);
			result.addObject("toAssign", true);
			result.addObject("conferenceId", submission.getConference().getId());
			result.addObject("requestURI", "submission/administrator/assign.do");
			result.addObject("lang", lang);
		} else if (submission != null && notificationElapsed == true) {
			result = this.conferenceAdministratorController.display(submission.getConference().getId());
			result.addObject("notificationMsg", "conference.notification.elapsed");
		} else
			result = new ModelAndView("redirect:misc/403");
		return result;
	}
	@RequestMapping(value = "/assignToReviewer", method = RequestMethod.GET)
	public ModelAndView assignToReviewer(@RequestParam final int submissionId, @RequestParam final int reviewerId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		this.submissionService.assignToReviewer(submissionId, reviewerId);

		result = this.conferenceAdministratorController.display(this.submissionService.findOne(submissionId).getConference().getId());
		result.addObject("isAdministrator", true);
		result.addObject("isAuthor", false);
		result.addObject("notificationMsgSuccess", "message.success.assign.reviewer");
		return result;
	}

	@RequestMapping(value = "/listAssignedReviewers", method = RequestMethod.GET)
	public ModelAndView listAssignedReviewers(@RequestParam final int submissionId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		final Submission submission = this.submissionService.findOne(submissionId);

		if (submission != null) {

			final Collection<Reviewer> reviewers = this.reviewerService.findReviewersAssignedToSubmission(submissionId);

			result = new ModelAndView("reviewer/list");
			result.addObject("reviewers", reviewers);
			result.addObject("submission", submission);
			result.addObject("isAdministrator", true);
			result.addObject("isAuthor", false);
			result.addObject("requestURI", "submission/administrator/listAssignedReviewers.do");
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
}
