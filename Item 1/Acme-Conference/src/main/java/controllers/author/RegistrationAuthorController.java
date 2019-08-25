
package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.ConferenceService;
import services.ConfigurationParametersService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	@Autowired
	private RegistrationService				registrationService;

	@Autowired
	private ConferenceService				conferenceService;

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Registration> registrations;
		final String rol = "author";

		final Author principal = this.authorService.findByPrincipal();
		registrations = this.registrationService.findAll();

		result = new ModelAndView("registration/list");
		result.addObject("registrations", registrations);
		result.addObject("rol", rol);
		result.addObject("theresConferenceAvailable", !this.conferenceAvailable(principal).isEmpty());
		result.addObject("requestURI", "registration/author/list.do");

		return result;
	}

	// Creation --------------------------------------------------------

	//@RequestParam(required = false, defaultValue = "0")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer conferenceId) {
		ModelAndView result;
		Registration registration;
		final Conference conference = this.conferenceService.findOne(conferenceId);

		boolean exists;

		final Author principal = this.authorService.findByPrincipal();
		registration = this.registrationService.create(principal, conference);

		result = this.createEditModelAndView(registration);
		result.addObject("conferenceAvailable", this.conferenceAvailable(principal));
		result.addObject("conference", conference);
		exists = this.conferenceService.exists(conferenceId);

		if (conferenceId != 0 && exists) {

		}

		return result;
	}

	private Collection<Conference> conferenceAvailable(final Author principal) {
		final Collection<Conference> hwConference = this.conferenceService.findAllByAuthorUserId(principal.getUserAccount().getId());
		final Collection<Conference> conference = this.conferenceService.findAll();
		conference.removeAll(hwConference);
		return conference;
	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int registrationId) {
		ModelAndView result;
		Registration registration;

		registration = this.registrationService.findOne(registrationId);
		if (registration == null)
			result = new ModelAndView("redirect:/misc/403.jsp");
		else {
			result = new ModelAndView("registration/display");
			result.addObject("registration", registration);
			result.addObject("rol", "author");
		}
		return result;
	}

	// ------------------------- Save -------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Registration registration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(registration);
		else
			try {
				this.registrationService.save(registration);
				result = new ModelAndView("redirect:/registration/author/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "registration.commit.error";
				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(registration, errorMessage);
			}
		return result;

	}
	// Delete --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Registration registration, final BindingResult binding) {
		ModelAndView result;

		try {
			this.registrationService.delete(registration);
			result = new ModelAndView("redirect:/author/list.do");
			final String banner = this.configurationParametersService.find().getBanner();
			result.addObject("banner", banner);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(registration, "registration.commit.error");
		}

		return result;
	}

	// Ancillary methods
	// --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Registration registration) {
		return this.createEditModelAndView(registration, null);
	}

	protected ModelAndView createEditModelAndView(final Registration registration, final String messageCode) {
		ModelAndView result;

		final Author h = this.authorService.findByPrincipal();

		final String rol = "author";

		result = new ModelAndView("registration/edit");
		result.addObject("registration", registration);
		result.addObject("conferenceAvailable", this.conferenceAvailable(h));
		result.addObject("rol", rol);
		result.addObject("message", messageCode);
		// the message code references an error message or null
		final String banner = this.configurationParametersService.find().getBanner();
		result.addObject("banner", banner);

		return result;
	}
}
