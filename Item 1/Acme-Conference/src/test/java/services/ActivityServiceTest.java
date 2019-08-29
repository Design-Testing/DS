
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Activity;
import domain.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActivityServiceTest extends AbstractTest {

	@Autowired
	ActivityService		activityService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createPresentationWithAuthenticationTest() {
		super.authenticate("admin1");
		final Activity activity = this.activityService.create();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPresentationWithoutAuthenticationTest() {
		final Activity activity = this.activityService.create();
	}

	@Test
	public void savetest() {
		super.authenticate("admin1");
		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAll());
		final Conference conference = conferences.get(0);
		final List<Activity> activities = new ArrayList<Activity>(this.activityService.findAll());
		final Activity activity = activities.get(0);
		this.activityService.save(activity, conference.getId());

	}

	/*
	 * @Test
	 * public void deleteWithConferenceInDraftModeTest() {
	 * super.authenticate("admin1");
	 * final Conference conference = this.conferenceService.findOne(3210);
	 * final Activity activity = this.activityService.findOne(3162);
	 * final Date submission = new Date(116, 5, 3);
	 * final Date notification = new Date(117, 5, 3);
	 * final Date cameraReady = new Date(118, 5, 3);
	 * final Date startDate = new Date(119, 5, 3);
	 * final Date endDate = new Date(120, 5, 3);
	 * conference.setIsDraft(true);
	 * conference.setSubmission(submission);
	 * conference.setCameraReady(cameraReady);
	 * conference.setNotification(notification);
	 * conference.setStartDate(startDate);
	 * conference.setEndDate(endDate);
	 * final Conference saved = this.conferenceService.save(conference);
	 * this.activityService.delete(activity, saved.getId());
	 * }
	 */
}
