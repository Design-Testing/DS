
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

import services.ActorService;
import services.FinderService;
import services.SponsorService;
import services.UserAccountService;
import domain.Finder;
import domain.Sponsor;
import forms.ActorForm;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	final String				lang	= LocaleContextHolder.getLocale().getLanguage();


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
	public ModelAndView signupSponsor() {
		final ModelAndView result;

		final ActorForm actorForm = new ActorForm();

		result = new ModelAndView("sponsor/signup");
		result.addObject("sponsor", actorForm);
		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsor(@ModelAttribute("sponsor") @Valid final ActorForm actorForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("sponsor/signup");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("sponsor", actorForm);

		} else
			try {
				Sponsor sponsor = this.sponsorService.reconstruct(actorForm, binding);
				final Finder finder = this.finderService.createForNewActor();
				sponsor.setFinder(finder);
				sponsor = this.sponsorService.save(sponsor);
				result = this.viewSponsor(sponsor.getId());
			} catch (final ValidationException oops) {
				result = new ModelAndView("sponsor/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("errors", "commit.error");
			}
		return result;
	}
}
