
package controllers.administrator;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConferenceService;
import services.QuoletService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController extends AbstractController {

	@Autowired
	private QuoletService						quoletService;

	@Autowired
	private AdministratorService				administratorService;

	@Autowired
	private ConferenceService					conferenceService;

	@Autowired
	private ConferenceAdministratorController	conferenceAdministratorController;


	// CREATE --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int conferenceId) {
		ModelAndView result;
		Quolet quolet;
		final Conference conference = this.conferenceService.findOne(conferenceId);
		quolet = this.quoletService.create(conference.getId());
		result = this.createEditModelAndView(quolet, conferenceId);

		return result;
	}

	// DISPLAY --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int quoletId) {
		final ModelAndView result;
		final Quolet quolet;
		final Administrator administrator;

		quolet = this.quoletService.findOne(quoletId);
		administrator = this.administratorService.findByPrincipal();

		if (quolet != null) {
			result = new ModelAndView("quolet/display");
			result.addObject("quolet", quolet);
			result.addObject("rol", "administrator");
		} else
			result = new ModelAndView("redirect:misc/403");

		return result;
	}
	// LIST --------------------------------------------------------

	@RequestMapping(value = "/allQuolets", method = RequestMethod.GET)
	public ModelAndView myQuolets() {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		this.administratorService.findByPrincipal();
		//quolets = this.quoletService.findQuoletsByAdministrator(administrator.getId());
		quolets = this.quoletService.findAll();

		result = new ModelAndView("quolet/list");
		result.addObject("quolets", quolets);
		result.addObject("rol", "administrator");
		result.addObject("requetURI", "quolet/administrator/allQuolets.do");
		//result.addObject("principalID", administrator.getId());

		return result;
	}

	// TO FINAL MODE --------------------------------------------------------

	@RequestMapping(value = "/finalMode", method = RequestMethod.GET)
	public ModelAndView finalMode(@RequestParam final int quoletId) {
		ModelAndView result;
		final Quolet quolet = this.quoletService.findOne(quoletId);

		if (quolet == null) {
			result = this.myQuolets();
			result.addObject("msg", "position.final.mode.error");
		} else
			try {
				this.quoletService.publish(quolet);
				result = this.conferenceAdministratorController.display(quolet.getConference().getId());
			} catch (final Throwable oops) {
				String errormsg = "position.final.mode.error";
				result = this.myQuolets();
				if (!quolet.getIsDraft())
					errormsg = "position.final.no.draft";
				result.addObject("msg", errormsg);
			}

		return result;
	}

	// EDIT --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int quoletId, @RequestParam final int conferenceId) {
		ModelAndView result;
		Quolet quolet;

		quolet = this.quoletService.findOne(quoletId);

		result = this.createEditModelAndView(quolet, conferenceId);

		return result;
	}

	// SAVE --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Quolet quolet, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String paramConferenceId;
		Integer conferenceId;

		paramConferenceId = request.getParameter("conferenceId");
		conferenceId = paramConferenceId.isEmpty() ? null : Integer.parseInt(paramConferenceId);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(quolet, conferenceId);
			result.addObject("errors", binding.getAllErrors());
		} else
			try {
				this.quoletService.save(quolet, conferenceId);
				result = this.conferenceConferenceorController.display(quolet.getConference().getId());
			} catch (final ValidationException oops) {
				result = this.createEditModelAndView(quolet, conferenceId, "commit.position.error");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(quolet, conferenceId, "commit.position.error");
				result.addObject("errors", binding.getAllErrors());
			}

		return result;
	}
	// DELETE --------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int quoletId) {
		final ModelAndView res;
		final Quolet quolet = this.quoletService.findOne(quoletId);
		res = this.conferenceAdministratorController.display(quolet.getConference().getId());

		try {
			this.quoletService.delete(quolet);
			res.addObject("msg", "delete.ok");
		} catch (final Throwable oops) {
			String error = "delete.error";
			if (oops.getMessage().contains(".error"))
				error = oops.getMessage();
			res.addObject("msg", error);
		}
		return res;
	}

	// ANCILLIARY METHODS --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Quolet quolet, final int conferenceId) {
		ModelAndView result;
		result = this.createEditModelAndView(quolet, conferenceId, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Quolet quolet, final int conferenceId, final String messageCode) {
		Assert.notNull(quolet);
		final ModelAndView result;
		final Conference conference = this.conferenceService.findOne(conferenceId);

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet); //this.constructPruned(position)
		result.addObject("message", messageCode);
		result.addObject("conference", conference);
		result.addObject("conferenceId", conferenceId);

		return result;
	}

}
