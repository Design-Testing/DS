
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ConferenceService;
import domain.Actor;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ActorService		actorService;

	final String				lang	= LocaleContextHolder.getLocale().getLanguage();


	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication authentication = context.getAuthentication();
		final Object principal = authentication.getPrincipal();

		Boolean isAuthor = false;
		if (!principal.toString().equals("anonymousUser")) {
			final Actor logged = this.actorService.findByPrincipal();
			final Authority authAuthor = new Authority();
			authAuthor.setAuthority("AUTHOR");
			if (logged.getUserAccount().getAuthorities().contains(authAuthor))
				isAuthor = true;

		}

		if (conference != null && conference.getIsDraft() == false) {
			result = new ModelAndView("conference/display");
			result.addObject("conference", conference);
			result.addObject("isAdministrator", false);
			result.addObject("isAuthor", isAuthor);
			result.addObject("lang", this.lang);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}

	// LIST --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findAll();

		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication authentication = context.getAuthentication();
		final Object principal = authentication.getPrincipal();

		Boolean isAuthor = false;
		if (!principal.toString().equals("anonymousUser")) {
			final Actor logged = this.actorService.findByPrincipal();
			final Authority authAuthor = new Authority();
			authAuthor.setAuthority("AUTHOR");
			if (logged.getUserAccount().getAuthorities().contains(authAuthor))
				isAuthor = true;

		}

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("isAuthor", isAuthor);
		result.addObject("requestURI", "conference/list.do");

		return result;
	}

	@RequestMapping(value = "/listFurthcoming", method = RequestMethod.GET)
	public ModelAndView listFurthcoming() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findFurthcomingConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requestURI", "conference/administrator/listFurthcoming.do");

		return result;
	}

	@RequestMapping(value = "/listPast", method = RequestMethod.GET)
	public ModelAndView listPast() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findPastConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requestURI", "conference/administrator/listPast.do");

		return result;
	}

	@RequestMapping(value = "/listRunning", method = RequestMethod.GET)
	public ModelAndView listRunning() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findRunningConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requestURI", "conference/administrator/listRunning.do");

		return result;
	}

}
