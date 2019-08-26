
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DashboardRepository;

@Service
@Transactional
public class DashboardService {

	@Autowired
	private DashboardRepository	dashboardRepository;


	/**
	 * Statistics of the number of submissions per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberSubmissionPerConference() {
		final Double[] res = this.dashboardRepository.getNumberSubmissionPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of registrations per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberRegistrationPerConference() {
		final Double[] res = this.dashboardRepository.getNumberRegistrationPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the conference fees.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getConferenceFees() {
		final Double[] res = this.dashboardRepository.getConferenceFees();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of days per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberOfDaysPerConference() {
		final Double[] res = this.dashboardRepository.getNumberOfDaysPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the number of conferences per category.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberOfConferencesPerCategory() {
		final Double[] res = this.dashboardRepository.getNumberOfConferencesPerCategory();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the number of the number of comments per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberCommentsPerConference() {
		final Double[] res = this.dashboardRepository.getNumberCommentsPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the number of the number of the number of comments per activity.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberCommentsPerActivity() {
		final Double[] res = this.dashboardRepository.getNumberCommentsPerActivity();
		Assert.notNull(res);
		return res;
	}

}
