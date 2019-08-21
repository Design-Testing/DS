
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.AdministratorService;
import services.SectionService;
import domain.Section;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	final String					lang	= LocaleContextHolder.getLocale().getLanguage();

	@Autowired
	private SectionService			sectionService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TutorialController		tutorialController;


	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		final Section section = this.sectionService.create();
		result = this.createEditModelAndView(section);
		result.addObject("tutorialId", tutorialId);
		return result;
	}
	// LIST  ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		ModelAndView result;
		result = new ModelAndView("section/list");

		final Collection<Section> sections = this.sectionService.findByTutorial(tutorialId);
		result.addObject("sections", sections);
		result.addObject("tutorialId", tutorialId);
		result.addObject("lang", this.lang);

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sectionId, @RequestParam final int tutorialId) {

		final ModelAndView result;
		final Section section = this.sectionService.findOne(sectionId);
		result = new ModelAndView("section/display");
		result.addObject("section", section);
		result.addObject("tutorialId", tutorialId);

		return result;
	}

	// UPDATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId, @RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.findOne(sectionId);

		if (section != null) {
			result = this.createEditModelAndView(section);
			result.addObject("tutorialId", tutorialId);
			result.addObject("section", section);

		} else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramTutorialId;
		Integer tutorialId;

		paramTutorialId = request.getParameter("tutorialId");
		tutorialId = paramTutorialId.isEmpty() ? null : Integer.parseInt(paramTutorialId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(section);
		else
			try {
				this.sectionService.save(section, tutorialId);
				result = this.list(tutorialId);
			} catch (final Throwable e) {
				result = this.createEditModelAndView(section, "section.commit.error");
			}
		return result;
	}

	// DELETE -----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sectionId, @RequestParam final int tutorialId, @RequestParam final int conferenceId) {

		ModelAndView res;
		final Section toDelete = this.sectionService.findOne(sectionId);

		try {
			this.sectionService.delete(toDelete, tutorialId, conferenceId);
			res = this.tutorialController.display(tutorialId, conferenceId);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect: display.do?sectionId=" + sectionId + "&tutorialId=" + tutorialId);
			final String error = "Cannot delete this section";
			res.addObject("error", error);
		}
		return res;
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Section section) {
		return this.createEditModelAndView(section, null);
	}

	protected ModelAndView createEditModelAndView(final Section section, final String messageCode) {
		final ModelAndView result;

		this.administratorService.findByPrincipal();

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("message", messageCode);

		return result;
	}

	// TODO: Pueden editar todos los admin o solo el suyo??
	/**
	 * Este metodo comprueba que haya un actor logeado y si dicho actor corresponde con un administrador añade al ModelAnView pasado como parametro un objeto "isAdmin" a true para indicar en la vista que es un administrador el que va a acceder a ella.
	 * 
	 * @param result
	 *            ModelAndView a modificar si el actor loggeado es un administrador
	 * @author a8081
	 */
	protected void addAdminIfLogged(final ModelAndView result) {
		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication authentication = context.getAuthentication();
		final Object principal = authentication.getPrincipal();

		if (principal != "anonymousUser" && principal != null) {
			final UserAccount user = (UserAccount) principal;
			if (user != null && this.administratorService.findByUserId(user.getId()) != null)
				result.addObject("isAdmin", true);
		}
	}

}
