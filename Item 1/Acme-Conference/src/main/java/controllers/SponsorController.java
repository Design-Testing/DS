
package controllers;

import java.util.Collection;

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

import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	private SponsorService	sponsorService;

	final String			lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewSponsor(@RequestParam final int sponsorId) {

		final ModelAndView result;
		final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
		result = new ModelAndView("sponsor/display");
		result.addObject("sponsor", sponsor);
		result.addObject("requestURI", "sponsor/display.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView viewSponsors() {
		this.sponsorService.findByPrincipal();

		final ModelAndView result;
		final Collection<Sponsor> sponsors = this.sponsorService.findAll();
		result = new ModelAndView("sponsor/list");
		result.addObject("sponsors", sponsors);
		result.addObject("isSponsor", true);
		result.addObject("requestURI", "sponsor/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editSponsorship(@RequestParam final int sponsorId) {
		final ModelAndView result;
		this.sponsorService.findByPrincipal();

		final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView createSponsorship() {
		final ModelAndView result;

		final Sponsor sponsor = this.sponsorService.create();
		result = new ModelAndView("sponsor/signup");
		result.addObject("sponsor", sponsor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsorship(@ModelAttribute("sponsor") @Valid final Sponsor sponsor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("sponsor/edit");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("sponsor", sponsor);

		} else
			try {
				this.sponsorService.save(sponsor);
				result = this.viewSponsor(sponsor.getId());
			} catch (final ValidationException oops) {
				result = new ModelAndView("sponsor/edit");
				result.addObject("sponsor", sponsor);
				result.addObject("errors", "commit.error");
			}
		return result;
	}
}
