
package controllers;

import java.util.ArrayList;

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
import domain.Finder;
import domain.Reviewer;
import forms.ReviewerForm;

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
		final ReviewerForm reviewerForm = new ReviewerForm();
		reviewerForm.setAddress(reviewer.getAddress());
		reviewerForm.setEmail(reviewer.getEmail());
		reviewerForm.setId(reviewer.getId());
		reviewerForm.setMiddleName(reviewer.getMiddleName());
		reviewerForm.setName(reviewer.getName());
		reviewerForm.setPhone(reviewer.getPhone());
		reviewerForm.setPhoto(reviewer.getPhoto());
		reviewerForm.setKeywords(reviewer.getKeywords());
		reviewerForm.setSurname(reviewer.getSurname());
		reviewerForm.setUserAccountpassword(reviewer.getUserAccount().getPassword());
		reviewerForm.setUserAccountuser(reviewer.getUserAccount().getUsername());
		final ModelAndView result;
		result = new ModelAndView("reviewer/edit");
		result.addObject("reviewerForm", reviewerForm);
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupReviewer() {
		final ModelAndView result;

		final ReviewerForm reviewerForm = new ReviewerForm();
		reviewerForm.setKeywords(new ArrayList<String>());
		result = new ModelAndView("reviewer/signup");
		result.addObject("reviewerForm", reviewerForm);
		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReviewer(@ModelAttribute("reviewerForm") @Valid final ReviewerForm reviewerForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("reviewer/signup");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("reviewerForm", reviewerForm);
		} else
			try {
				final Reviewer reviewer = this.reviewerService.reconstruct(reviewerForm, binding);
				this.reviewerService.save(reviewer);
				if (reviewerForm.getId() == 0) {
					final Finder finder = this.finderService.createForNewActor();
					reviewer.setFinder(finder);
					this.reviewerService.save(reviewer);
					result = new ModelAndView("forward:/security/login.do");
				} else {
					this.reviewerService.save(reviewer);
					result = this.viewReviewer();
				}
			} catch (final ValidationException oops) {
				result = new ModelAndView("reviewer/signup");
				result.addObject("reviewerForm", reviewerForm);
				result.addObject("errors", "commit.error");
			} catch (final Throwable e) {
				result = new ModelAndView("reviewer/signup");
				if (e.getMessage().contains("Username is already in use"))
					result.addObject("alert", "reviewer.usernameIsUsed");
				else if (e.getMessage().contains("Email is already in use"))
					result.addObject("alert", "reviewer.emailIsUsed");
				result.addObject("errors", "commit.error");
				result.addObject("reviewerForm", reviewerForm);
			}

		return result;
	}
}
