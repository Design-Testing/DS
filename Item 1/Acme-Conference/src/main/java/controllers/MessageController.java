
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import services.TopicService;
import domain.Actor;
import domain.Message;

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
		result = new ModelAndView("message/edit");
		final Message message = this.messageService.create();
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		final Actor principal = this.actorService.findByPrincipal();
		if (!binding.hasErrors()) {
			this.messageService.send(message);
			result = new ModelAndView("message/list");
			result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		} else {
			result = new ModelAndView("message/edit");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("message", message);
		}

		return result;

	}

	@RequestMapping(value = "/output", method = RequestMethod.GET)
	public ModelAndView output() {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("message/list");

		result.addObject("topics", this.topicService.findAll());

		result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		return result;
	}

	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public ModelAndView inbox() {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("topics", this.topicService.findAll());

		result.addObject("messages", this.messageService.findAllByRecipient(principal.getId()));
		return result;
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();
		result = new ModelAndView("message/list");

		result.addObject("topics", this.topicService.findAll());

		final List<Message> mensajes = new ArrayList<>();
		mensajes.addAll(this.messageService.findAllByRecipient(principal.getId()));
		mensajes.addAll(this.messageService.findAllBySender(principal.getId()));

		result.addObject("messages", mensajes);
		return result;
	}

	@RequestMapping(value = "/listByTopic", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam final int topicId) {
		ModelAndView result;
		//final Actor principal = this.actorService.findByPrincipal();
		result = new ModelAndView("message/list");

		result.addObject("topics", this.topicService.findAll());

		result.addObject("messages", this.messageService.findAllByTopic(topicId));
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor principal = this.actorService.findByPrincipal();
		final Message message = this.messageService.findOne(messageId);
		this.messageService.delete(message);
		result = new ModelAndView("message/list");

		result.addObject("topics", this.topicService.findAll());

		result.addObject("messages", this.messageService.findAllBySender(principal.getId()));
		return result;
	}

}
