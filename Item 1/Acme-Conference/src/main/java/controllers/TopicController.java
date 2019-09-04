
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TopicService;
import domain.Topic;

@Controller
@RequestMapping("/topic")
public class TopicController extends AbstractController {

	@Autowired
	private TopicService	topicService;


	// Constructors -----------------------------------------------------------
	public TopicController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("topic/list");
		result.addObject("requestURI", "topic/list.do");
		result.addObject("topics", this.topicService.findAll());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int topicId) {
		final ModelAndView result;
		result = new ModelAndView("topic/list");
		result.addObject("requestURI", "topic/list.do");

		try {
			final Topic topic = this.topicService.findOne(topicId);
			this.topicService.delete(topic);
			result.addObject("msg", "delete.ok");
		} catch (final Throwable oops) {
			String errorMessage = "topic.commit.error";
			if (oops.getMessage().contains(".error"))
				errorMessage = oops.getMessage();
			result.addObject("msg", errorMessage);
		}

		result.addObject("topics", this.topicService.findAll());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@Valid final Topic topic, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(topic);
		else
			try {
				this.topicService.save(topic);
				result = new ModelAndView("redirect:/topic/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "topic.commit.error";
				if (oops.getMessage().contains(".error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(topic, errorMessage);
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Topic topic) {
		return this.createEditModelAndView(topic, null);
	}

	protected ModelAndView createEditModelAndView(final Topic topic, final String message) {
		ModelAndView result;
		result = new ModelAndView("topic/edit");
		result.addObject("topic", topic);
		result.addObject("message", message);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int topicId) {
		ModelAndView result;
		result = new ModelAndView("topic/edit");
		result.addObject("topic", this.topicService.findOne(topicId));
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = new ModelAndView("topic/edit");
		final Topic topic = this.topicService.create();
		result.addObject("topic", topic);
		return result;
	}

}
