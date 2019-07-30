
package controllers.author;

import java.util.Collection;

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

	final String				lang	= LocaleContextHolder.getLocale().getLanguage();


	// CREATE  ---------------------------------------------------------------
	/** Cuando pulsa sobre el botón submit al lado de una conference **/
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;

		this.authorService.findByPrincipal();

		result = new ModelAndView("submission/editReviewPaper"); //para rellenar el reviw paper
		result.addObject("conferenceId", conferenceId);
		result.addObject("lang", this.lang);
		return result;
	}

	// SAVE --------------------------------------------------------

	/** Cuando ha rellenado el papel review y manda la presentación finalmente **/
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView submit(@Valid final Paper paper, final BindingResult binding, @RequestParam final int conferenceId, final HttpServletRequest request) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("submission/editReviewPaper");
			result.addObject("paper", paper);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("lang", this.lang);
		} else
			try {
				final Submission submission = this.submissionService.create(conferenceId);
				this.submissionService.submits(submission, paper);
				result = this.mySubmissions();
			} catch (final ValidationException oops) {
				result = new ModelAndView("submission/editReviewPaper");
				result.addObject("paper", paper);
				result.addObject("lang", this.lang);
				result.addObject("errors", "commit.error");
			} catch (final Throwable oops) {
				result = new ModelAndView("submission/editReviewPaper");
				result.addObject("paper", paper);
				result.addObject("lang", this.lang);
				//if (oops.getMessage().equals("no deadline or date can be null"))
				//result.addObject("msgerror", "conference.error.empty");
				result.addObject("errors", binding.getAllErrors());

			}

		return result;
	}

	// CREATE  ---------------------------------------------------------------
	/** Cuando pulsa sobre el botón enviar camera-ready paper sobre una submission **/
	@RequestMapping(value = "/createPaper", method = RequestMethod.GET)
	public ModelAndView createPaper(@RequestParam final int submissionId) {
		ModelAndView result;

		this.authorService.findByPrincipal();

		result = new ModelAndView("submission/editCameraReadyPaper"); //para rellenar el camara ready paper
		result.addObject("submissionId", submissionId);
		result.addObject("lang", this.lang);
		return result;
	}

	// SAVE --------------------------------------------------------

	/** Cuando ha rellenado el papel review y manda la presentación finalmente **/
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "sendPaper")
	public ModelAndView sendCameraReadyPaper(@Valid final Paper paper, final BindingResult binding, @RequestParam final int submissionId, final HttpServletRequest request) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("submission/editCameraReadyPaper");
			result.addObject("paper", paper);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("lang", this.lang);
		} else
			try {
				this.submissionService.sendCameraReadyPaper(submissionId, paper);
				result = this.mySubmissions();
			} catch (final ValidationException oops) {
				result = new ModelAndView("submission/editCameraReadyPaper");
				result.addObject("paper", paper);
				result.addObject("lang", this.lang);
				result.addObject("errors", "commit.error");
			} catch (final Throwable oops) {
				result = new ModelAndView("submission/editCameraReadyPaper");
				result.addObject("paper", paper);
				result.addObject("lang", this.lang);
				//if (oops.getMessage().equals("no deadline or date can be null"))
				//result.addObject("msgerror", "conference.error.empty");
				result.addObject("errors", binding.getAllErrors());

			}

		return result;
	}

	// LIST --------------------------------------------------------

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

	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int submissionId) {
		ModelAndView result;

		final Author principal = this.authorService.findByPrincipal();

		final Submission submission = this.submissionService.findOne(submissionId);

		Boolean visible = false;

		if (submission.getAuthor().equals(principal))
			visible = true;

		if (submission != null && visible == true) {
			result = new ModelAndView("submission/display");
			result.addObject("submission", submission);
			result.addObject("isAdministrator", false);
			result.addObject("isAuthor", true);
			result.addObject("lang", this.lang);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
}
