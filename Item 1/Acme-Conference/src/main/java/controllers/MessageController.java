
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import services.MessageService;
import services.TopicService;
import domain.Actor;
import domain.Message;
import domain.Topic;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private TopicService	topicService;


	public MessageController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();
		result = new ModelAndView("message/edit");

		final Collection<Topic> topics = this.topicService.findAll();

		final Message message = this.messageService.create();

		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		final boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		result.addObject("isAdmin", isAdmin);
		result.addObject("topics", topics);
		result.addObject("m", message);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("m") @Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();
		if (!binding.hasErrors()) {
			this.messageService.send(message);
			result = new ModelAndView("message/list");
			result.addObject("topics", this.topicService.findAll());
			final Collection<Actor> actors = this.actorService.findAll();
			result.addObject("actors", actors);

			result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		} else {
			result = new ModelAndView("message/edit");
			final boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
			result.addObject("isAdmin", isAdmin);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("m", message);
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

		final List<Message> mensajes = new ArrayList<>();
		mensajes.addAll(this.messageService.findAllByRecipient(principal.getId()));
		mensajes.addAll(this.messageService.findAllBySender(principal.getId()));

		result.addObject("messages", mensajes);
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
		return result;
	}

	@RequestMapping(value = "/listByRecipient", method = RequestMethod.GET)
	public ModelAndView listByRecipient(@RequestParam final int actorId) {
		ModelAndView result;

		result = new ModelAndView("message/list");

		result.addObject("topics", this.topicService.findAll());

		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);

		result.addObject("messages", this.messageService.findAllByRecipient(actorId));
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();
		final Message message = this.messageService.findOne(messageId);
		this.messageService.delete(message);
		result = new ModelAndView("message/list");

		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);

		result.addObject("topics", this.topicService.findAll());

		result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();
		final Message message = this.messageService.findOne(messageId);

		result = new ModelAndView("message/display");

		result.addObject("m", message);
		return result;
	}

	@RequestMapping(value = "/broadcastAuthors", method = RequestMethod.POST)
	public ModelAndView broadcastAuthors(@ModelAttribute("m") @Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();
		if (!binding.hasErrors()) {
			this.messageService.broadcastToAllAuthors(message);
			result = new ModelAndView("message/list");
			result.addObject("topics", this.topicService.findAll());
			final Collection<Actor> actors = this.actorService.findAll();
			result.addObject("actors", actors);

			result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		} else {

			result = new ModelAndView("message/edit");
			final boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
			result.addObject("isAdmin", isAdmin);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("m", message);
		}

		return result;

	}

	@RequestMapping(value = "/broadcastActors", method = RequestMethod.POST)
	public ModelAndView broadcastActors(@ModelAttribute("m") @Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();

		if (!binding.hasErrors()) {
			this.messageService.broadcastToAllActors(message);
			result = new ModelAndView("message/list");
			result.addObject("topics", this.topicService.findAll());
			final Collection<Actor> actors = this.actorService.findAll();
			result.addObject("actors", actors);

			result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		} else {
			result = new ModelAndView("message/edit");
			final boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
			result.addObject("isAdmin", isAdmin);
			result.addObject("errors", binding.getAllErrors());
			result.addObject("m", message);
		}

		return result;

	}

}
