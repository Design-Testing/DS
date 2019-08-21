
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

import services.AdministratorService;
import services.ConferenceService;
import services.PresentationService;
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


	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		final Presentation presentation = (Presentation) this.presentationService.create();
		result = this.createEditModelAndView(presentation);
		result.addObject("conferenceId", conferenceId);
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

		return result;
	}

	// UPDATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int presentationId, @RequestParam final int conferenceId) {
		ModelAndView result;
		Presentation presentation;

		presentation = this.presentationService.findOne(presentationId);

		if (presentation != null) {
			result = this.createEditModelAndView(presentation);
			result.addObject("conferenceId", conferenceId);
			result.addObject("presentation", presentation);

		} else
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
			result = this.createEditModelAndView(presentation);
		else
			try {
				this.presentationService.save(presentation, conferenceId);
				result = this.list(conferenceId);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(presentation, "presentation.commit.error");
			}
		return result;
	}
	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Presentation presentation) {
		return this.createEditModelAndView(presentation, null);
	}

	protected ModelAndView createEditModelAndView(final Presentation presentation, final String messageCode) {
		final ModelAndView result;

		this.administratorService.findByPrincipal();

		result = new ModelAndView("presentation/edit");
		result.addObject("presentation", presentation);
		result.addObject("message", messageCode);

		return result;
	}

}
