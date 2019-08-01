
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TopicService;

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
		result.addObject("topics", this.topicService.findAll());
		return result;
	}

}
