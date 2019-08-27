
package controllers.author;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.PaperService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Paper;
import domain.Submission;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private PaperService		paperService;


	// CREATE  SUBMISSION (AND REVIEW PAPER)---------------------------------------------------------------
	/** Cuando pulsa sobre el botón submit al lado de una conference **/
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		this.authorService.findByPrincipal();

		final Paper paper = this.paperService.create();

		result = new ModelAndView("submission/editReviewPaper"); //para rellenar el reviw paper
		result.addObject("conferenceId", conferenceId);
		result.addObject("paper", paper);
		result.addObject("lang", lang);
		return result;
	}

	// SAVE SUBMISSION (AND REVIEW PAPER)--------------------------------------------------------

	/** Cuando ha rellenado el papel review y manda la presentación finalmente **/
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@Valid final Paper paper, final BindingResult binding, @RequestParam final String conferenceId, final HttpServletRequest request) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		if (binding.hasErrors()) {
			result = new ModelAndView("submission/editReviewPaper");
			result.addObject("paper", paper);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("conferenceId", conferenceId);
			result.addObject("lang", lang);
		} else
			try {
				final Submission submission = this.submissionService.create(Integer.parseInt(conferenceId));
				this.submissionService.submits(submission, paper);
				result = this.mySubmissions();

			} catch (final ValidationException oops) {
				result = new ModelAndView("submission/editReviewPaper");
				result.addObject("paper", paper);
				result.addObject("lang", lang);
				result.addObject("conferenceId", conferenceId);
				result.addObject("errors", "commit.error");
			} catch (final Throwable oops) {
				result = new ModelAndView("submission/editReviewPaper");
				result.addObject("paper", paper);
				result.addObject("conferenceId", conferenceId);
				result.addObject("lang", lang);
				if (oops.getMessage().equals("submission deadline is elapsed"))
					result.addObject("msgerror", "error.submission.elapsed");
				result.addObject("errors", binding.getAllErrors());

			}

		return result;
	}

	// CREATE CAMERA-READY PAPER ---------------------------------------------------------------
	/** Cuando pulsa sobre el botón enviar camera-ready paper sobre una submission **/
	@RequestMapping(value = "/createPaper", method = RequestMethod.GET)
	public ModelAndView createPaper(@RequestParam final int submissionId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		this.authorService.findByPrincipal();

		final Paper paper = this.paperService.create();

		result = new ModelAndView("submission/editCameraReadyPaper"); //para rellenar el camara ready paper
		result.addObject("submissionId", submissionId);
		result.addObject("paper", paper);
		result.addObject("lang", lang);
		return result;
	}

	// EDIT CAMERA-READY PAPER --------------------------------------------------------

	@RequestMapping(value = "/editPaper", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int submissionId, @RequestParam final int paperId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		final Author principal = this.authorService.findByPrincipal();

		final Submission submission = this.submissionService.findOne(submissionId);

		final Paper paper = this.paperService.findOne(paperId);

		if (submission.getAuthor().equals(principal) && submission.getStatus().equals("ACCEPTED") && submission.getCameraReadyPaper().equals(paper)) {
			result = new ModelAndView("submission/editCameraReadyPaper");
			result.addObject("submissionId", submissionId);
			result.addObject("isAuthor", true);
			result.addObject("paper", paper);
			result.addObject("lang", lang);
		}

		else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
	// SAVE CAMERA-READY PAPER--------------------------------------------------------

	/** guardar camera ready paper **/
	@RequestMapping(value = "/editPaper", method = RequestMethod.POST, params = "savePaper")
	public ModelAndView sendCameraReadyPaper(@Valid final Paper paper, final BindingResult binding, @RequestParam final String submissionId, final HttpServletRequest request) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		this.authorService.findByPrincipal();

		if (binding.hasErrors()) {
			result = new ModelAndView("submission/editCameraReadyPaper");
			result.addObject("paper", paper);
			result.addObject("submissionId", submissionId);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("lang", lang);
		} else
			try {
				this.submissionService.sendCameraReadyPaper(Integer.parseInt(submissionId), paper);
				result = this.display(Integer.parseInt(submissionId));
			} catch (final ValidationException oops) {
				result = new ModelAndView("submission/editCameraReadyPaper");
				result.addObject("paper", paper);
				result.addObject("lang", lang);
				result.addObject("submissionId", submissionId);
				result.addObject("errors", "commit.error");
			} catch (final Throwable oops) {
				result = new ModelAndView("submission/editCameraReadyPaper");
				result.addObject("paper", paper);
				result.addObject("submissionId", submissionId);
				result.addObject("lang", lang);
				result.addObject("errors", binding.getAllErrors());

			}

		return result;
	}

	// LIST SUBMISSIONS OF AUTHOR PRINCIPAL --------------------------------------------------------

	@RequestMapping(value = "/mySubmissions", method = RequestMethod.GET)
	public ModelAndView mySubmissions() {
		final ModelAndView result;

		this.authorService.findByPrincipal();
		final Collection<Submission> submissions = this.submissionService.getSubmissionsByAuthor(this.authorService.findByPrincipal().getId());

		result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("isAdministrator", false);
		result.addObject("isAuthor", true);
		result.addObject("requestURI", "submission/author/mySubmissions.do");

		return result;
	}

	// DISPLAY SUBMISSION --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int submissionId) {
		ModelAndView result;

		final String lang = LocaleContextHolder.getLocale().getLanguage();

		final Author principal = this.authorService.findByPrincipal();

		final Submission submission = this.submissionService.findOne(submissionId);

		Boolean visible = false;

		Boolean availableCameraReadyDeadline = false;

		Boolean availableSubmissionStatus = false;

		final Date now = new Date();

		if (now.before(submission.getConference().getCameraReady()))
			availableCameraReadyDeadline = true;
		if (submission.getStatus().equals("ACCEPTED"))
			availableSubmissionStatus = true;

		if (submission.getAuthor().equals(principal))
			visible = true;

		if (submission != null && visible == true) {
			result = new ModelAndView("submission/display");
			result.addObject("submission", submission);
			result.addObject("isAdministrator", false);
			result.addObject("isAuthor", true);
			result.addObject("availableCameraReadyDeadline", availableCameraReadyDeadline);
			result.addObject("availableSubmissionStatus", availableSubmissionStatus);
			result.addObject("lang", lang);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
}
