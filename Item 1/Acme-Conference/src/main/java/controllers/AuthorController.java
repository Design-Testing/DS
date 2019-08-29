
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

import services.ActorService;
import services.AuthorService;
import services.FinderService;
import services.UserAccountService;
import domain.Author;
import forms.ActorForm;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	final String				lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewAuthor() {
		final Author author = this.authorService.findByPrincipal();
		ModelAndView result;
		result = new ModelAndView("author/display");
		result.addObject("author", author);
		result.addObject("requestURI", "author/display.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editAuthor() {
		final Author author = this.authorService.findByPrincipal();
		final ActorForm actorForm = new ActorForm();
		actorForm.setAddress(author.getAddress());
		actorForm.setEmail(author.getEmail());
		actorForm.setId(author.getId());
		actorForm.setMiddleName(author.getMiddleName());
		actorForm.setName(author.getName());
		actorForm.setPhone(author.getPhone());
		actorForm.setPhoto(author.getPhoto());
		actorForm.setSurname(author.getSurname());
		actorForm.setUserAccountpassword(author.getUserAccount().getPassword());
		actorForm.setUserAccountuser(author.getUserAccount().getUsername());
		final ModelAndView result;
		result = new ModelAndView("author/edit");
		result.addObject("actorForm", actorForm);
		return result;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupAuthor() {
		final ModelAndView result;

		final ActorForm actorForm = new ActorForm();

		result = new ModelAndView("author/signup");
		result.addObject("actorForm", actorForm);
		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuthor(@ModelAttribute("actorForm") @Valid final ActorForm actorForm, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("author/signup");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("actorForm", actorForm);
		} else if (actorForm.getId() == 0)
			try {
				Author author = this.authorService.reconstruct(actorForm, binding);
				author = this.authorService.save(author);
				result = new ModelAndView("forward:/security/login.do");
			} catch (final ValidationException oops) {
				result = new ModelAndView("author/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("errors", "commit.error");
			} catch (final Throwable e) {
				result = new ModelAndView("author/signup");
				if (e.getMessage().contains("Username is already in use"))
					result.addObject("alert", "author.usernameIsUsed");
				else if (e.getMessage().contains("Email is already in use"))
					result.addObject("alert", "author.emailIsUsed");
				result.addObject("errors", "commit.error");
				result.addObject("actorForm", actorForm);
			}
		else
			try {
				final Author author = this.authorService.reconstruct(actorForm, binding);
				this.authorService.save(author);
				result = this.viewAuthor();
			} catch (final ValidationException oops) {
				result = new ModelAndView("author/signup");
				result.addObject("actorForm", actorForm);
				result.addObject("errors", "commit.error");
			} catch (final Throwable e) {
				result = new ModelAndView("author/signup");
				if (e.getMessage().contains("Username is already in use"))
					result.addObject("alert", "author.usernameIsUsed");
				else if (e.getMessage().contains("Email is already in use"))
					result.addObject("alert", "author.emailIsUsed");
				result.addObject("errors", "commit.error");
				result.addObject("actorForm", actorForm);
			}

		return result;
	}
}
