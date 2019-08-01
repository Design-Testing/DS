
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView viewConfiguration() {
		this.administratorService.findByPrincipal();

		final ModelAndView result;
		final Collection<ConfigurationParameters> configurationParameters = this.configurationParameterService.findAll();
		result = new ModelAndView("configurationParameters/list");
		result.addObject("configurationParameters", configurationParameters);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "configurationParameters/list.do");

		return result;
	}

}
