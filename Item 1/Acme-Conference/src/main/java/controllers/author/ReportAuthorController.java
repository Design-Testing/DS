
package controllers.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ReportService;
import controllers.AbstractController;

@Controller
@RequestMapping("/report/author")
public class ReportAuthorController extends AbstractController {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public ReportAuthorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		ModelAndView result;
		result = new ModelAndView("report/display");
		result.addObject("report", this.reportService.findOne(reportId));
		return result;
	}

	@RequestMapping(value = "/listBySubmission", method = RequestMethod.GET)
	public ModelAndView listBySubmission(@RequestParam final int submissionId) {
		ModelAndView result;
		result = new ModelAndView("report/list");
		result.addObject("reports", this.reportService.findReportsBySubmission(submissionId));
		return result;
	}

}
