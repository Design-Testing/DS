
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DashboardService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService	dashboardService;


	@RequestMapping(value = "get", method = RequestMethod.GET)
	public ModelAndView get() {
		ModelAndView result;
		result = new ModelAndView("dashboard/display");

		final Double[] SubmissionPerConference = this.dashboardService.getNumberSubmissionPerConference();
		result.addObject("SubmissionPerConferenceAvg", SubmissionPerConference[0]);
		result.addObject("SubmissionPerConferenceMin", SubmissionPerConference[1]);
		result.addObject("SubmissionPerConferenceMax", SubmissionPerConference[2]);
		result.addObject("SubmissionPerConferenceStd", SubmissionPerConference[3]);

		final Double[] RegistrationPerConference = this.dashboardService.getNumberRegistrationPerConference();
		result.addObject("RegistrationPerConferenceAvg", RegistrationPerConference[0]);
		result.addObject("RegistrationPerConferenceMin", RegistrationPerConference[1]);
		result.addObject("RegistrationPerConferenceMax", RegistrationPerConference[2]);
		result.addObject("RegistrationPerConferenceStd", RegistrationPerConference[3]);

		final Double[] ConferenceFees = this.dashboardService.getConferenceFees();
		result.addObject("ConferenceFeesAvg", ConferenceFees[0]);
		result.addObject("ConferenceFeesMin", ConferenceFees[1]);
		result.addObject("ConferenceFeesMax", ConferenceFees[2]);
		result.addObject("ConferenceFeesStd", ConferenceFees[3]);

		final Double[] DaysPerConference = this.dashboardService.getNumberOfDaysPerConference();
		result.addObject("DaysPerConferenceAvg", DaysPerConference[0]);
		result.addObject("DaysPerConferenceMin", DaysPerConference[1]);
		result.addObject("DaysPerConferenceMax", DaysPerConference[2]);
		result.addObject("DaysPerConferenceStd", DaysPerConference[3]);

		final Double[] ConferencesPerCategory = this.dashboardService.getNumberOfConferencesPerCategory();
		result.addObject("ConferencesPerCategoryAvg", ConferencesPerCategory[0]);
		result.addObject("ConferencesPerCategoryMin", ConferencesPerCategory[1]);
		result.addObject("ConferencesPerCategoryMax", ConferencesPerCategory[2]);
		result.addObject("ConferencesPerCategoryStd", ConferencesPerCategory[3]);

		final Double[] CommentsPerConference = this.dashboardService.getNumberCommentsPerConference();
		result.addObject("CommentsPerConferenceAvg", CommentsPerConference[0]);
		result.addObject("CommentsPerConferenceMin", CommentsPerConference[1]);
		result.addObject("CommentsPerConferenceMax", CommentsPerConference[2]);
		result.addObject("CommentsPerConferenceStd", CommentsPerConference[3]);

		final Double[] CommentsPerActivity = this.dashboardService.getNumberCommentsPerActivity();
		result.addObject("CommentsPerActivityAvg", CommentsPerActivity[0]);
		result.addObject("CommentsPerActivityMin", CommentsPerActivity[1]);
		result.addObject("CommentsPerActivityMax", CommentsPerActivity[2]);
		result.addObject("CommentsPerActivityStd", CommentsPerActivity[3]);

		return result;
	}
}
