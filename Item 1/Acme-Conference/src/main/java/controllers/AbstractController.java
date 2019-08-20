/*
 * AbstractController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationParametersService;

@Controller
public class AbstractController {

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		return result;
	}

	@ModelAttribute(value = "bannerURL")
	public String getBannerURL() {
		final String result = this.configurationParametersService.findBanner();
		return result;
	}

	@ModelAttribute(value = "lang")
	public String getLang() {
		final String lang = LocaleContextHolder.getLocale().getLanguage();
		return lang;
	}

	//	@ModelAttribute(value = "sysName")
	//	public String getSysName() {
	//		final String result = this.configurationParametersService.findSysName();
	//		return result;
	//	}

}
