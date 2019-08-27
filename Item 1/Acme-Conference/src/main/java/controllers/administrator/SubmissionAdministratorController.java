
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ReportService;
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
	private ReportService						reportService;

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

		Boolean alreadyAssignThree = false;

		if (this.reportService.findReportsBySubmission(submissionId).size() >= 3)
			alreadyAssignThree = true;

		if (submission != null) {
			result = new ModelAndView("submission/listReviewers");
			result.addObject("submissionId", submissionId);
			result.addObject("reviewers", reviewers);
			result.addObject("isAdministrator", true);
			result.addObject("isAuthor", false);
			result.addObject("alreadyAssignThree", alreadyAssignThree);
			result.addObject("toAssign", true);
			result.addObject("conferenceId", submission.getConference().getId());
			result.addObject("requestURI", "submission/administrator/assign.do");
			result.addObject("lang", lang);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
	@RequestMapping(value = "/assignToReviewer", method = RequestMethod.GET)
	public ModelAndView assignToReviewer(@RequestParam final int submissionId, @RequestParam final int reviewerId) {
		ModelAndView result;

		this.administratorService.findByPrincipal();

		this.submissionService.assignToReviewer(submissionId, reviewerId);

		final Collection<Submission> submissions = this.submissionService.findAll();

		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("isAdministrator", true);
		result.addObject("isAuthor", false);
		result.addObject("requestURI", "submission/administrator/submissions.do");
		result.addObject("messageSuccessAssign", true);
		return result;
	}

}
