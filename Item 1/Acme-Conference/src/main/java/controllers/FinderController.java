
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConferenceService;
import services.FinderService;
import domain.Conference;
import domain.Finder;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private CategoryService		categoryService;

	final String				lang	= LocaleContextHolder.getLocale().getLanguage();


	// SEARCH  ---------------------------------------------------------------

	@RequestMapping(value = "/searching", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		result = new ModelAndView("finder/search");
		result.addObject("lang", this.lang);
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final String keyword) {
		ModelAndView result;
		try {
			final Collection<Conference> conferences = this.conferenceService.findConferences(keyword);
			result = new ModelAndView("finder/search");
			result.addObject("lang", this.lang);
			result.addObject("conferences", conferences);
		} catch (final Throwable e) {
			result = new ModelAndView("finder/search");
			result.addObject("errortrace", "finder.commit.error");
		}
		return result;
	}

	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Finder finder = this.finderService.create();
		result = this.createEditModelAndView(finder);
		return result;
	}

	// UPDATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/actor/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Finder finder;
		finder = this.finderService.findActorFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);
		return result;
	}
	// CLEAR  ---------------------------------------------------------------		

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				final Finder cleared = this.finderService.clear(finder);
				result = this.createEditModelAndView(cleared);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/actor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				final Finder saved = this.finderService.find(finder);
				result = this.createEditModelAndView(saved);
				result.addObject("requestURI", "finder/actor/edit.do");
			} catch (final Throwable e) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;
		boolean deadnul = false;

		if (finder.getFromDate() != null && finder.getToDate() != null)
			deadnul = !finder.getFromDate().before(finder.getToDate());

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", messageCode);
		result.addObject("deadnul", deadnul);
		result.addObject("lang", this.lang);
		result.addObject("categories", this.categoryService.findCategoriesName(this.lang));

		return result;
	}
}
