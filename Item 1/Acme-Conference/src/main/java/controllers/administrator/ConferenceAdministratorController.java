
package controllers.administrator;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.CategoryRepository;
import services.AdministratorService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;
import forms.ConferenceForm;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CategoryRepository		categoryRepository;

	final String					lang	= LocaleContextHolder.getLocale().getLanguage();


	// CREATE  ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int categoryId) {
		ModelAndView result;
		final Conference conference = this.conferenceService.create();
		result = new ModelAndView("conference/edit");
		result.addObject("conferenceForm", this.conferenceService.constructPruned(conference)); //this.constructPruned(position)
		result.addObject("categoryId", categoryId);
		result.addObject("isAdministrator", true);
		result.addObject("message", null);
		return result;
	}

	// LIST --------------------------------------------------------

	@RequestMapping(value = "/myConferences", method = RequestMethod.GET)
	public ModelAndView myConferences() {
		final ModelAndView result;

		this.administratorService.findByPrincipal();
		final Collection<Conference> conferences = this.conferenceService.findAll();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", true);
		result.addObject("requetURI", "conference/administrator/myConferences.do");

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
					errormsg = "error.already.draft";
				result.addObject("msg", errormsg);
				result.addObject("isAdministrator", true);
			}

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
			result.addObject("categories", this.categoryRepository.findAll());
			result.addObject("lang", this.lang);
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
				result.addObject("errors", "commit.lesson.error");
			} catch (final Throwable oops) {
				result = new ModelAndView("conference/edit");
				result.addObject("conferenceForm", conferenceForm);
				result.addObject("isAdministrator", true);
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

		if (conference != null) {
			result = new ModelAndView("conference/display");
			result.addObject("conference", conference);
			result.addObject("isAdministrator", true);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}

}
