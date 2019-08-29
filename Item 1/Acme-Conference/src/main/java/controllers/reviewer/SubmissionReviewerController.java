
package controllers.reviewer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SubmissionService;
import controllers.AbstractController;
import domain.Submission;

@Controller
@RequestMapping("/submission/reviewer")
public class SubmissionReviewerController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int submissionId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		final Submission submission = this.submissionService.findOne(submissionId);

		final Boolean visible = false;

		Boolean availableCameraReadyDeadline = false;

		Boolean availableSubmissionStatus = false;

		final Date now = new Date();

		if (now.before(submission.getConference().getCameraReady()))
			availableCameraReadyDeadline = true;
		if (submission.getStatus().equals("ACCEPTED"))
			availableSubmissionStatus = true;

		if (submission != null) {
			result = new ModelAndView("submission/display");
			result.addObject("submission", submission);
			result.addObject("isAdministrator", false);
			result.addObject("isAuthor", false);
			result.addObject("availableCameraReadyDeadline", availableCameraReadyDeadline);
			result.addObject("availableSubmissionStatus", availableSubmissionStatus);
			result.addObject("lang", lang);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
}
