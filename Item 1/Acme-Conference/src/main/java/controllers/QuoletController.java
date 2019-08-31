
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/auditor")
public class QuoletController extends AbstractController {

	@Autowired
	private QuoletService	quoletService;

	final String			lang	= LocaleContextHolder.getLocale().getLanguage();


	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int quoletId) {
		final ModelAndView result;
		final Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);

		final boolean visible = quolet.getIsDraft().equals(false);

		if (quolet != null && visible) {
			result = new ModelAndView("quolet/display");
			result.addObject("quolet", quolet);
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
	// LIST --------------------------------------------------------

	@RequestMapping(value = "/listQuolets", method = RequestMethod.GET)
	public ModelAndView listQuolets(@RequestParam final int conferenceId) {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		quolets = this.quoletService.findQuoletsByConference(conferenceId);

		result = new ModelAndView("quolet/list");
		result.addObject("quolets", quolets);
		result.addObject("requetURI", "quolet/listQuolets.do");

		return result;
	}

}
