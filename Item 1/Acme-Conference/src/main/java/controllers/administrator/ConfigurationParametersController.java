
package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

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

import services.AdministratorService;
import services.ConfigurationParametersService;
import controllers.AbstractController;
import domain.ConfigurationParameters;

@Controller
@RequestMapping("/configurationParameters/administrator")
public class ConfigurationParametersController extends AbstractController {

	@Autowired
	private ConfigurationParametersService	configurationParameterService;

	@Autowired
	private AdministratorService			administratorService;

	final String							lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewConfiguration() {
		this.administratorService.findByPrincipal();

		final ModelAndView result;
		final List<ConfigurationParameters> configurationParametersAll = new ArrayList<ConfigurationParameters>(this.configurationParameterService.findAll());
		result = new ModelAndView("configurationParameters/display");
		final ConfigurationParameters configurationParameters = configurationParametersAll.get(0);
		result.addObject("configurationParameters", configurationParameters);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "configurationParameters/display.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editConfiguration(@RequestParam final int configurationParametersId) {
		final ModelAndView result;
		this.administratorService.findByPrincipal();

		final ConfigurationParameters configurationParameters = this.configurationParameterService.findOne(configurationParametersId);
		result = new ModelAndView("configurationParameters/edit");
		result.addObject("configurationParameters", configurationParameters);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveConfiguration(@ModelAttribute("configurationParameters") @Valid final ConfigurationParameters configurationParameters, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("configurationParameters/edit");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("configurationParameters", configurationParameters);

		} else
			try {
				this.configurationParameterService.save(configurationParameters);
				result = this.viewConfiguration();
			} catch (final ValidationException oops) {
				result = new ModelAndView("configurationParameters/edit");
				result.addObject("configurationParameters", configurationParameters);
				result.addObject("errors", "commit.error");
			}
		return result;
	}
}
