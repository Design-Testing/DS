
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (conference != null && conference.getIsDraft() == false) {
			result = new ModelAndView("conference/display");
			result.addObject("conference", conference);
			result.addObject("isAdministrator", false);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}

	// LIST --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findAll();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requetURI", "conference/list.do");

		return result;
	}

	public ModelAndView listFurthcoming() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findFurthcomingConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requetURI", "conference/administrator/listFurthcoming.do");

		return result;
	}

	@RequestMapping(value = "/listPast", method = RequestMethod.GET)
	public ModelAndView listPast() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findPastConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requetURI", "conference/administrator/listPast.do");

		return result;
	}

	@RequestMapping(value = "/listRunning", method = RequestMethod.GET)
	public ModelAndView listRunning() {
		final ModelAndView result;

		final Collection<Conference> conferences = this.conferenceService.findRunningConferences();

		result = new ModelAndView("conference/list");
		result.addObject("conferences", conferences);
		result.addObject("isAdministrator", false);
		result.addObject("requetURI", "conference/administrator/listRunning.do");

		return result;
	}

}
