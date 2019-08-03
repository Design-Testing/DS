
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

import services.SponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	final String				lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewSponsorship(@RequestParam final int sponsorshipId) {
		this.sponsorService.findByPrincipal();

		final ModelAndView result;
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/display");
		result.addObject("sponsorship", sponsorship);
		result.addObject("isSponsor", true);
		result.addObject("requestURI", "sponsorship/display.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView viewSponsorships() {
		this.sponsorService.findByPrincipal();

		final ModelAndView result;
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAll();
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("isSponsor", true);
		result.addObject("requestURI", "sponsorship/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editSponsorship(@RequestParam final int sponsorshipId) {
		final ModelAndView result;
		this.sponsorService.findByPrincipal();

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createSponsorship() {
		final ModelAndView result;
		this.sponsorService.findByPrincipal();

		final Sponsorship sponsorship = this.sponsorshipService.create();
		result = new ModelAndView("sponsorship/create");
		result.addObject("sponsorship", sponsorship);
		result.addObject("sponsor", this.sponsorService.findByPrincipal());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsorship(@ModelAttribute("sponsorship") @Valid final Sponsorship sponsorship, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("sponsorship/edit");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("sponsorship", sponsorship);

		} else
			try {
				this.sponsorshipService.save(sponsorship);
				result = this.viewSponsorship(sponsorship.getId());
			} catch (final ValidationException oops) {
				result = new ModelAndView("sponsorship/edit");
				result.addObject("sponsorship", sponsorship);
				result.addObject("errors", "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteSponsorship(@RequestParam final int sponsorshipId) {
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		this.sponsorshipService.delete(sponsorship);
		final ModelAndView result = new ModelAndView("sponsorship/delete");
		result.addObject("requestURI", "sponsorship/list.do");
		return result;
	}
}
