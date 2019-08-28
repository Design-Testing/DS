
package controllers.sponsor;

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

import services.ConfigurationParametersService;
import services.SponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService				sponsorshipService;

	@Autowired
	private SponsorService					sponsorService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	final String							lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewSponsorship(@RequestParam final int sponsorshipId) {
		this.sponsorService.findByPrincipal();
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllBySponsor();
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		final ModelAndView result;
		if (sponsorships.contains(sponsorship)) {
			result = new ModelAndView("sponsorship/display");
			result.addObject("sponsorship", sponsorship);
			result.addObject("isSponsor", true);
			result.addObject("requestURI", "sponsorship/display.do");
		} else
			result = new ModelAndView("sponsorship/list");

		return result;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView viewSponsorships() {
		this.sponsorService.findByPrincipal();

		final ModelAndView result;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllBySponsor();
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("isSponsor", true);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editSponsorship(@RequestParam final int sponsorshipId) {
		ModelAndView result = new ModelAndView();
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		final Collection<String> makes = this.configurationParametersService.find().getCreditCardMake();

		if (sponsor.getId() == sponsorship.getSponsor().getId()) {
			result = new ModelAndView("sponsorship/edit");
			result.addObject("sponsorship", sponsorship);
			result.addObject("makes", makes);
		} else
			result = new ModelAndView("forward:/sponsorship/sponsor/list.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createSponsorship() {
		final ModelAndView result;
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		final Collection<String> makes = this.configurationParametersService.find().getCreditCardMake();
		System.out.println(makes);

		final Sponsorship sponsorship = this.sponsorshipService.create();
		sponsorship.setSponsor(sponsor);
		result = new ModelAndView("sponsorship/create");
		result.addObject("sponsorship", sponsorship);
		result.addObject("sponsor", this.sponsorService.findByPrincipal());
		result.addObject("makes", makes);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsorship(@ModelAttribute("sponsorship") @Valid final Sponsorship sponsorship, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		final Collection<String> makes = this.configurationParametersService.find().getCreditCardMake();

		if (binding.hasErrors()) {
			result = new ModelAndView("sponsorship/edit");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("sponsorship", sponsorship);
			result.addObject("makes", makes);

		} else if (sponsor.getId() == sponsorship.getSponsor().getId())
			try {
				final Sponsorship s = this.sponsorshipService.save(sponsorship);
				result = this.viewSponsorship(s.getId());
			} catch (final ValidationException oops) {
				result = new ModelAndView("sponsorship/edit");
				result.addObject("sponsorship", sponsorship);
				result.addObject("errors", "commit.error");
			}
		else
			result = new ModelAndView("forward:/sponsorship/sponsor/list.do");

		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteSponsorship(@RequestParam final int sponsorshipId) {
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		ModelAndView result;
		if (sponsor.getId() == sponsorship.getSponsor().getId()) {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("sponsorship/list");
			result.addObject("requestURI", "sponsorship/list.do");
		} else
			result = new ModelAndView("sponsorship/sponsor/list");

		return result;
	}
}
