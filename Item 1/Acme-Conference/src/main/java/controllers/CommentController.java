
package controllers;

import java.util.List;

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

import services.ActivityService;
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

	@Autowired
	private ActivityService	activityService;


	// CREATE  ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int entityId, @RequestParam final String entity) {
		ModelAndView result;
		final Comment comment = this.commentService.create(entity, entityId);
		result = this.createEditModelAndView(comment);
		return result;
	}
	// LIST  ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final String entity, @RequestParam final int id) {
		ModelAndView result;
		result = new ModelAndView("comment/list");
		switch (entity) {
		case "activity":
			result.addObject("comments", this.commentService.findByActivity(id));
			break;
		case "report":
			result.addObject("comments", this.commentService.findByReport(id));
			break;
		case "conference":
			result.addObject("comments", this.commentService.findByConference(id));
			break;
		}
		// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo
		//		case "quolet":
		//			result.addObject("comments", this.commentService.findByQuolet(id));
		//			break;

		result.addObject("id", id);
		result.addObject("entity", "conference");
		result.addObject("lang", this.lang);

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
		if (entity == "activity")
			result.addObject("entity", this.activityService.identifyActivity(comment.getActivity()));
		result.addObject("lastURL", "comment/list.do?id=" + entityId + "&entity=" + entity);

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
				this.commentService.save(comment);
				final List<Object> info = this.commentService.findRelationEntity(comment);
				result = this.list((String) info.get(0), (int) info.get(1));
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
