
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

import services.FinderService;
import services.ReviewerService;
import domain.Reviewer;
import forms.ActorForm;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController extends AbstractController {

	@Autowired
	private ReviewerService	reviewerService;

	@Autowired
	private FinderService	finderService;

	final String			lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewReviewer() {
		final Reviewer reviewer = this.reviewerService.findByPrincipal();
		ModelAndView result;
		result = new ModelAndView("reviewer/display");
		result.addObject("reviewer", reviewer);
		result.addObject("requestURI", "reviewer/display.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editReviewer() {
		final Reviewer reviewer = this.reviewerService.findByPrincipal();
		final ActorForm actorForm = new ActorForm();
		actorForm.setAddress(reviewer.getAddress());
		actorForm.setEmail(reviewer.getEmail());
		actorForm.setId(reviewer.getId());
		actorForm.setMiddleName(reviewer.getMiddleName());
		actorForm.setName(reviewer.getName());
		actorForm.setPhone(reviewer.getPhone());
		actorForm.setPhoto(reviewer.getPhoto());
		actorForm.setSurname(reviewer.getSurname());
		actorForm.setUserAccountpassword(reviewer.getUserAccount().getPassword());
		actorForm.setUserAccountuser(reviewer.getUserAccount().getUsername());
		final ModelAndView result;
		result = new ModelAndView("reviewer/edit");
		result.addObject("actorForm", actorForm);
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupReviewer() {
		final ModelAndView result;

		final ActorForm actorForm = new ActorForm();

		result = new ModelAndView("reviewer/signup");
		result.addObject("actorForm", actorForm);
		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReviewer(@ModelAttribute("actorForm") @Valid final ActorForm actorForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("reviewer/signup");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("actorForm", actorForm);
		} else if (actorForm.getId() == 0)
			try {
				Reviewer reviewer = this.reviewerService.reconstruct(actorForm, binding);
				reviewer = this.reviewerService.save(reviewer);
				result = new ModelAndView("forward:/security/login.do");
			} catch (final ValidationException oops) {
				result = new ModelAndView("reviewer/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("errors", "commit.error");
			} catch (final Throwable e) {
				result = new ModelAndView("reviewer/signup");
				if (e.getMessage().contains("Username is already in use"))
					result.addObject("alert", "reviewer.usernameIsUsed");
				else if (e.getMessage().contains("Email is already in use"))
					result.addObject("alert", "reviewer.emailIsUsed");
				result.addObject("errors", "commit.error");
				result.addObject("actorForm", actorForm);
			}
		else
			try {
				final Reviewer reviewer = this.reviewerService.reconstruct(actorForm, binding);
				this.reviewerService.save(reviewer);
				result = this.viewReviewer();
			} catch (final ValidationException oops) {
				result = new ModelAndView("reviewer/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("errors", "commit.error");
			} catch (final Throwable e) {
				result = new ModelAndView("reviewer/signup");
				if (e.getMessage().contains("Username is already in use"))
					result.addObject("alert", "reviewer.usernameIsUsed");
				else if (e.getMessage().contains("Email is already in use"))
					result.addObject("alert", "reviewer.emailIsUsed");
				result.addObject("errors", "commit.error");
				result.addObject("actorForm", actorForm);
			}

		return result;
	}
}
