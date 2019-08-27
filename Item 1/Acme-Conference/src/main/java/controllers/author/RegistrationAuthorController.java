
package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import domain.CreditCard;
import domain.Registration;
import forms.RegistrationForm;

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


	//	AUTHOR
	//	Manage his or her registrations, which includes: registering to a conference as long
	//	as it has not started, listing them, and displaying their details.

	// Listing --------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Registration> registrations;

		final Author principal = this.authorService.findByPrincipal();
		registrations = this.registrationService.findAll(principal);

		result = new ModelAndView("registration/list");
		result.addObject("registrations", registrations);

		result.addObject("requestURI", "registration/author/list.do");

		return result;
	}

	// Creation --------------------------------------------------------

	//@RequestParam(required = false, defaultValue = "0")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		ModelAndView result;
		final Conference conference = this.conferenceService.findOne(conferenceId);
		final Author principal = this.authorService.findByPrincipal();

		final Registration registration = this.registrationService.create(principal, conference);
		// this.conferenceService.exists(conferenceId);
		result = this.createEditModelAndView(registration);

		return result;
	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int registrationId) {
		ModelAndView result;
		Registration registration;

		registration = this.registrationService.findOne(registrationId);

		Assert.isTrue(registration.getAuthor().equals(this.authorService.findByPrincipal()));
		result = new ModelAndView("registration/display");
		result.addObject("registration", registration);
		result.addObject("rol", "author");

		return result;
	}

	@RequestMapping(value = "/displayFromConference", method = RequestMethod.GET)
	public ModelAndView displayFromConference(@RequestParam final int conferenceId) {
		ModelAndView result;
		Registration registration;

		registration = this.registrationService.findByConferenceAndPrincipal(conferenceId);

		result = new ModelAndView("registration/display");
		result.addObject("registration", registration);
		result.addObject("rol", "author");

		return result;
	}

	// ------------------------- Edit -------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int registrationId) {
		ModelAndView result;
		Registration registration;

		registration = this.registrationService.findOne(registrationId);

		if (registration != null)
			result = this.createEditModelAndView(registration);
		else
			result = new ModelAndView("redirect:/misc/403.jsp");

		return result;
	}

	// ------------------------- Save -------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(registrationForm, null);
		else
			try {
				final Registration registration = this.registrationService.reconstruct(registrationForm, binding);
				this.registrationService.save(registration);
				result = new ModelAndView("redirect:/registration/author/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "registration.commit.error";
				if (oops.getMessage().contains(".error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(registrationForm, errorMessage);
			}
		return result;
	}
	// Delete --------------------------------------------------------

	//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	//	public ModelAndView delete(@RequestParam final int registrationId) {
	//		ModelAndView result;
	//
	//		final Registration registration = this.registrationService.findOne(registrationId);
	//
	//		try {
	//			this.registrationService.delete(registration);
	//			result = new ModelAndView("redirect:/author/list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(registration, "registration.commit.error");
	//		}
	//
	//		return result;
	//	}

	// --------------------------------------------------------
	// Ancillary methods
	// --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Registration registration) {
		return this.createEditModelAndView(registration, null);
	}

	protected ModelAndView createEditModelAndView(final Registration registration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("registration/edit");
		result.addObject("registrationForm", this.constructPruned(registration));
		result.addObject("makes", this.configurationParametersService.find().getCreditCardMake());
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RegistrationForm registrationForm, final String messageCode) {
		ModelAndView result;

		this.authorService.findByPrincipal();

		result = new ModelAndView("registration/edit");
		result.addObject("registrationForm", registrationForm);
		result.addObject("makes", this.configurationParametersService.find().getCreditCardMake());
		result.addObject("message", messageCode);

		return result;
	}

	private RegistrationForm constructPruned(final Registration registration) {
		final RegistrationForm pruned = new RegistrationForm();

		if (registration.getId() != 0) {
			pruned.setExpirationMonth(registration.getCreditCard().getExpirationMonth());
			pruned.setExpirationYear(registration.getCreditCard().getExpirationYear());
			pruned.setCvv(registration.getCreditCard().getCvv());
			pruned.setMake(registration.getCreditCard().getMake());
			pruned.setHolderName(registration.getCreditCard().getHolderName());
			pruned.setNumber(registration.getCreditCard().getNumber());
		}

		pruned.setId(registration.getId());
		pruned.setVersion(registration.getVersion());
		pruned.setAuthor(registration.getAuthor());
		pruned.setConference(registration.getConference());

		return pruned;
	}

	private CreditCard constructCreditCard(final RegistrationForm registrationForm) {
		final CreditCard creditCard = new CreditCard();
		creditCard.setHolderName(registrationForm.getHolderName());
		creditCard.setCvv(registrationForm.getCvv());
		creditCard.setExpirationMonth(registrationForm.getExpirationMonth());
		creditCard.setExpirationYear(registrationForm.getExpirationYear());
		creditCard.setMake(registrationForm.getCvv());
		creditCard.setNumber(registrationForm.getNumber());
		return creditCard;
	}

}
