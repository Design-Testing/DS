
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AuthorService;
import services.ConferenceService;
import services.MessageService;
import services.TopicService;
import domain.Actor;
import domain.Author;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService		messageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TopicService		topicService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private AuthorService		authorService;


	public MessageController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final String origen, final Integer entityId) {
		final Message message = this.messageService.create();
		final ModelAndView result = this.createEditModelAndView(message);
		result.addObject("origen", origen);
		result.addObject("entityId", entityId);
		String action = "message/edit.do?origen=" + origen;
		if (entityId != null)
			action += "&entityId" + entityId;
		result.addObject("action", action);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("m") @Valid final Message message, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message, null);
		else
			try {
				this.messageService.send(message);
				result = this.multipleSources(request);
			} catch (final Throwable oops) {
				String errorMessage = "message.commit.error";
				if (oops.getMessage().contains(".error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(message, errorMessage);
			}
		return result;
	}
	@RequestMapping(value = "/outbox", method = RequestMethod.GET)
	public ModelAndView outbox() {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("message/list");

		result.addObject("topics", this.topicService.findAll());
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		result.addObject("origen", "outbox");
		result.addObject("requestURI", "message/outbox.do");

		return result;
	}

	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public ModelAndView inbox() {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("topics", this.topicService.findAll());
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("messages", this.messageService.findAllByRecipient(principal.getId()));
		result.addObject("origen", "inbox");
		result.addObject("requestURI", "message/inbox.do");

		return result;
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("message/list");
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("topics", this.topicService.findAll());
		result.addObject("messages", this.messageService.findAllByPrincipal(principal.getId()));
		result.addObject("origen", "all");
		result.addObject("requestURI", "message/listAll.do");

		return result;
	}

	@RequestMapping(value = "/listByTopic", method = RequestMethod.GET)
	public ModelAndView listByTopic(@RequestParam final int topicId) {
		ModelAndView result;

		result = new ModelAndView("message/list");
		result.addObject("topics", this.topicService.findAll());
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("messages", this.messageService.findAllByTopic(topicId));
		result.addObject("origen", "topic");
		result.addObject("entityId", topicId);
		result.addObject("requestURI", "message/listByTopic.do");

		return result;
	}

	@RequestMapping(value = "/listBySender", method = RequestMethod.GET)
	public ModelAndView listBySender(@RequestParam final int actorId) {
		ModelAndView result;

		result = new ModelAndView("message/list");
		result.addObject("topics", this.topicService.findAll());
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("messages", this.messageService.findAllBySender(actorId));
		result.addObject("entityId", actorId);
		result.addObject("requestURI", "message/listBySender.do");

		return result;
	}

	@RequestMapping(value = "/listByRecipient", method = RequestMethod.GET)
	public ModelAndView listByRecipient(@RequestParam final int actorId) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("topics", this.topicService.findAll());
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("messages", this.messageService.findAllByRecipient(principal.getId(), actorId));
		result.addObject("origen", "recipient");
		result.addObject("entityId", actorId);
		result.addObject("requestURI", "message/listByRecipient.do");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId, final HttpServletRequest request) {
		final ModelAndView result = this.multipleSources(request);

		final Message message = this.messageService.findOne(messageId);

		try {
			this.messageService.delete(message);
		} catch (final Throwable oops) {
			result.addObject("msg", oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;

		final Message message = this.messageService.findOne(messageId);

		result = new ModelAndView("message/display");
		result.addObject("m", message);

		return result;
	}

	@RequestMapping(value = "/broadcastAuthors", method = RequestMethod.POST)
	public ModelAndView broadcastAuthors(@ModelAttribute("m") @Valid final Message message, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		if (binding.hasErrors() && !binding.hasFieldErrors("recivers"))
			result = this.createEditModelAndView(message, null);
		else
			try {
				this.messageService.broadcastToAllAuthors(message);
				result = this.multipleSources(request);
			} catch (final Throwable oops) {
				String errorMessage = "message.commit.error";
				if (oops.getMessage().contains(".error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(message, errorMessage);
			}
		return result;
	}

	@RequestMapping(value = "/broadcastAuthorsSubmissionConference", method = RequestMethod.GET)
	public ModelAndView broadcastAuthorsSubmissionConference(@RequestParam final String origen, final Integer conferenceId) {
		final Message message = this.messageService.create();
		final ModelAndView result = this.createEditModelAndView(message);
		final Collection<Author> autores = this.authorService.findAuthorsByConferenceToSubmissions(conferenceId);

		final Collection<Actor> actores = new ArrayList<Actor>();
		actores.addAll(autores);
		message.setRecivers(actores);
		result.addObject("isBroadcast", true);
		result.addObject("origen", origen);
		result.addObject("entityId", null);
		final String action = "message/edit.do?origen=" + origen;
		result.addObject("action", action);
		return result;
	}
	@RequestMapping(value = "/broadcastAuthorsRegistrationConference", method = RequestMethod.GET)
	public ModelAndView broadcastAuthorsRegistrationConference(@RequestParam final String origen, final Integer conferenceId) {
		final Message message = this.messageService.create();
		final ModelAndView result = this.createEditModelAndView(message);
		final Collection<Author> autores = this.authorService.findAuthorsByConferenceToRegistrations(conferenceId);

		final Collection<Actor> actores = new ArrayList<Actor>();
		actores.addAll(autores);
		message.setRecivers(actores);
		result.addObject("isBroadcast", true);
		result.addObject("origen", origen);
		result.addObject("entityId", null);
		final String action = "message/edit.do?origen=" + origen;
		result.addObject("action", action);
		return result;
	}
	@RequestMapping(value = "/broadcastActors", method = RequestMethod.POST)
	public ModelAndView broadcastActors(@ModelAttribute("m") @Valid final Message message, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		if (binding.hasErrors() && !binding.hasFieldErrors("recivers"))
			result = this.createEditModelAndView(message, null);
		else
			try {
				this.messageService.broadcastToAllActors(message);
				result = this.multipleSources(request);
			} catch (final Throwable oops) {
				String errorMessage = "message.commit.error";
				if (oops.getMessage().contains(".error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(message, errorMessage);
			}
		return result;

	}

	// --------------------------------------------------------
	// Ancillary methods
	// --------------------------------------------------------

	private ModelAndView multipleSources(final HttpServletRequest request) {
		ModelAndView result;
		String paramId;
		Integer entityId;
		final String paramOrigen = request.getParameter("origen");
		switch (paramOrigen) {
		case "inbox":
			result = new ModelAndView("redirect:/message/inbox.do");
			break;
		case "outbox":
			result = new ModelAndView("redirect:/message/outbox.do");
			break;
		case "recipient":
			paramId = request.getParameter("entityId");
			entityId = paramId.isEmpty() ? null : Integer.parseInt(paramId);
			result = new ModelAndView("redirect:/message/listByRecipient.do?actorId=" + entityId);
			break;
		case "sender":
			paramId = request.getParameter("entityId");
			entityId = paramId.isEmpty() ? null : Integer.parseInt(paramId);
			result = new ModelAndView("redirect:/message/listBySender.do?actorId=" + entityId);
			break;
		case "topic":
			paramId = request.getParameter("entityId");
			entityId = paramId.isEmpty() ? null : Integer.parseInt(paramId);
			result = new ModelAndView("redirect:/message/listByTopic.do?topicId=" + entityId);
			break;
		default:
			result = new ModelAndView("redirect:/message/listAll.do");
			break;
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message) {
		return this.createEditModelAndView(message, null);
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();
		result = new ModelAndView("message/edit");
		result.addObject("m", message);
		result.addObject("topics", this.topicService.findAll());
		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("message", messageCode);
		final boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		result.addObject("isAdmin", isAdmin);
		result.addObject("conferences", this.conferenceService.findAll());

		return result;
	}

}
