
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ReportService;
import domain.Report;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public ReportController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("report/list");
		result.addObject("reports", this.reportService.findReportsByReviewer(this.actorService.findByPrincipal().getId()));
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;
		if (!binding.hasErrors()) {
			this.reportService.save(report, report.getSubmission(), report.getReviewer());
			result = new ModelAndView("redirect:/report/list.do");
		} else {
			result = new ModelAndView("report/edit");
			result.addObject("report", report);
			result.addObject("errors", binding.getAllErrors());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		ModelAndView result;
		result = new ModelAndView("report/edit");
		result.addObject("report", this.reportService.findOne(reportId));
		return result;
	}

}
