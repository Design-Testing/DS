
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;

	final String			lang	= LocaleContextHolder.getLocale().getLanguage();


	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int entityId, @RequestParam final String entity) {
		ModelAndView result;
		final Comment comment = this.commentService.create(entity, entityId);
		result = this.createEditModelAndView(comment);
		return result;
	}
	// LIST  ---------------------------------------------------------------		

	// Sustituir "ClassName" por el nombre de la nueva clase sobre la que se quiere redactar los comentarios
	// 
	//	@RequestMapping(value = "/listClassName", method = RequestMethod.GET)
	//	public ModelAndView listPresentation(@RequestParam final int classNameId) {
	//		ModelAndView result;
	//		result = new ModelAndView("comment/list");
	//		result.addObject("comments", this.commentService.findByClassName(classNameId));
	//		return result;
	//	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listConference(@RequestParam final String entity, @RequestParam final int id) {
		ModelAndView result;
		result = new ModelAndView("comment/list");
		switch (entity) {
		case "panel":
			result.addObject("comments", this.commentService.findByPanel(id));
			break;
		case "presentation":
			result.addObject("comments", this.commentService.findByPresentation(id));
			break;
		case "tutorial":
			result.addObject("comments", this.commentService.findByTutorial(id));
			break;
		case "conference":
			result.addObject("comments", this.commentService.findByConference(id));
			break;
		}
		result.addObject("id", id);
		result.addObject("entity", "conference");

		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication authentication = context.getAuthentication();
		final Object principal = authentication.getPrincipal();

		if (principal != "anonymousUser")
			result.addObject("principalId", this.actorService.findByPrincipal().getId());
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId, @RequestParam final String entity, @RequestParam final int entityId) {

		final ModelAndView result;
		final Comment comment = this.commentService.findOne(commentId);
		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		result.addObject("lastURL", "comment/" + entity + "/list.do?id=" + entityId);

		return result;
	}

	// UPDATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final Comment comment = this.commentService.create();
		Assert.notNull(comment);
		return this.createEditModelAndView(comment);
	}

	// SAVE  ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				final Comment saved = this.commentService.save(comment);
				result = this.createEditModelAndView(saved);
				result.addObject("lang", this.lang);
				result.addObject("requestURI", "comment/edit.do");
			} catch (final Throwable e) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	}

	// CREATEEDITMODELANDVIEW -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		return this.createEditModelAndView(comment, null);
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", messageCode);

		return result;
	}
}
