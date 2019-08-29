
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConferenceService;
import services.PaperService;
import services.PresentationService;
import domain.Paper;
import domain.Presentation;

@Controller
@RequestMapping("/presentation")
public class PresentationController extends AbstractController {

	final String					lang	= LocaleContextHolder.getLocale().getLanguage();

	@Autowired
	private PresentationService		presentationService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PaperService			paperService;


	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId, final String fromConferenceDisplay) {
		ModelAndView result;
		final Presentation presentation = this.presentationService.create();
		result = this.createEditModelAndView(presentation, conferenceId);
		result.addObject("conferenceId", conferenceId);
		result.addObject("fromConferenceDisplay", fromConferenceDisplay);
		return result;
	}
	// LIST  ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView result;
		result = new ModelAndView("presentation/list");

		final Collection<Presentation> presentations = this.presentationService.findPresentationsByConference(conferenceId);
		result.addObject("presentations", presentations);
		result.addObject("conferenceId", conferenceId);
		final boolean isDraft = this.conferenceService.findOne(conferenceId).getIsDraft();
		result.addObject("isDraft", isDraft);

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int presentationId, @RequestParam final int conferenceId) {

		final ModelAndView result;
		final Presentation presentation = this.presentationService.findOne(presentationId);
		result = new ModelAndView("presentation/display");
		result.addObject("presentation", presentation);
		result.addObject("conferenceId", conferenceId);
		final boolean isDraft = this.conferenceService.findOne(conferenceId).getIsDraft();
		result.addObject("isDraft", isDraft);

		return result;
	}

	// UPDATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int presentationId, @RequestParam final int conferenceId) {
		ModelAndView result;
		Presentation presentation;

		presentation = this.presentationService.findOne(presentationId);

		if (presentation != null)
			result = this.createEditModelAndView(presentation, conferenceId);
		else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Presentation presentation, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramPresentationId;
		Integer conferenceId;

		paramPresentationId = request.getParameter("conferenceId");
		conferenceId = paramPresentationId.isEmpty() ? null : Integer.parseInt(paramPresentationId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(presentation, conferenceId);
		else
			try {
				this.presentationService.save(presentation, conferenceId);
				result = this.list(conferenceId);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(presentation, "presentation.commit.error", conferenceId);
			}
		return result;
	}

	// DELETE -----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int conferenceId, @RequestParam final int presentationId) {

		ModelAndView res;
		final Presentation toDelete = this.presentationService.findOne(presentationId);

		try {
			this.presentationService.delete(toDelete, conferenceId);
			res = this.list(conferenceId);
		} catch (final Throwable oops) {
			res = this.display(presentationId, conferenceId);
			final String error = "delete.error";
			res.addObject("error", error);
		}
		return res;
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Presentation presentation, final int conferenceId) {
		return this.createEditModelAndView(presentation, null, conferenceId);
	}

	protected ModelAndView createEditModelAndView(final Presentation presentation, final String messageCode, final int conferenceId) {
		final ModelAndView result;

		this.administratorService.findByPrincipal();

		final Collection<Paper> papers = this.paperService.findAll();

		result = new ModelAndView("presentation/edit");
		result.addObject("presentation", presentation);
		result.addObject("message", messageCode);
		result.addObject("actors", this.actorService.findAll());
		result.addObject("papers", papers);
		result.addObject("conferenceId", conferenceId);

		return result;
	}

}
