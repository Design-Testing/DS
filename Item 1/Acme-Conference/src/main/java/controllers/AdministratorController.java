
package controllers;

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
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationParametersService;
import services.FinderService;
import domain.Administrator;
import domain.Finder;
import forms.AdminForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private FinderService					finderService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	final String							lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewAdministrator() {
		final Administrator administrator = this.administratorService.findByPrincipal();
		ModelAndView result;
		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);
		result.addObject("requestURI", "administrator/display.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editAdministrator() {
		final Administrator administrator = this.administratorService.findByPrincipal();
		final AdminForm actorForm = new AdminForm();
		actorForm.setAddress(administrator.getAddress());
		actorForm.setEmail(administrator.getEmail());
		actorForm.setId(administrator.getId());
		actorForm.setMiddleName(administrator.getMiddleName());
		actorForm.setName(administrator.getName());
		actorForm.setPhone(administrator.getPhone());
		actorForm.setPhoto(administrator.getPhoto());
		actorForm.setSurname(administrator.getSurname());
		actorForm.setUserAccountpassword(administrator.getUserAccount().getPassword());
		actorForm.setUserAccountuser(administrator.getUserAccount().getUsername());
		final ModelAndView result;
		result = new ModelAndView("administrator/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupAdministrator() {
		final ModelAndView result;

		final AdminForm actorForm = new AdminForm();

		result = new ModelAndView("administrator/signup");
		result.addObject("actorForm", actorForm);
		result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());

		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@ModelAttribute("actorForm") @Valid final AdminForm actorForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("administrator/signup");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("actorForm", actorForm);
			result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());

		} else if (actorForm.getId() == 0)
			try {
				Administrator administrator = this.administratorService.reconstruct(actorForm, binding);
				final Finder finder = this.finderService.createForNewActor();
				administrator.setFinder(finder);
				administrator = this.administratorService.save(administrator);
				result = new ModelAndView("forward:/security/login.do");
			} catch (final ValidationException oops) {
				result = new ModelAndView("administrator/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());
			} catch (final Throwable e) {
				result = new ModelAndView("administrator/signup");
				String error = "commit.error";
				if (e.getMessage().contains(".error"))
					error = e.getMessage();
				result.addObject("message", error);
				result.addObject("actorForm", actorForm);
				result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());

			}
		else
			try {
				final Administrator administrator = this.administratorService.reconstruct(actorForm, binding);
				this.administratorService.save(administrator);
				result = this.viewAdministrator();
			} catch (final ValidationException oops) {
				result = new ModelAndView("administrator/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("errors", "commit.error");
				result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());

			} catch (final Throwable e) {
				result = new ModelAndView("administrator/signup");
				String error = "commit.error";
				if (e.getMessage().contains(".error"))
					error = e.getMessage();
				result.addObject("message", error);
				result.addObject("actorForm", actorForm);
				result.addObject("countryPhoneCode", this.configurationParametersService.find().getCountryPhoneCode());

			}

		return result;
	}
}
