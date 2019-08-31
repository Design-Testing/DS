
package services;

import java.util.ArrayList;
import java.util.Date;
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
	public void createTest() {
		super.authenticate("admin1");
		final Activity activity = this.activityService.create();
		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		super.authenticate("admin1");
		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAll());
		final Conference conference = conferences.get(1);
		conference.setIsDraft(true);
		final Date submission = new Date(116, 5, 3);
		final Date notification = new Date(117, 5, 3);
		final Date cameraReady = new Date(118, 5, 3);
		final Date startDate = new Date(119, 5, 3);
		final Date endDate = new Date(120, 5, 3);
		conference.setIsDraft(true);
		conference.setSubmission(submission);
		conference.setCameraReady(cameraReady);
		conference.setNotification(notification);
		conference.setStartDate(startDate);
		conference.setEndDate(endDate);
		final List<Activity> activities = new ArrayList<Activity>(this.activityService.findAll());
		final Activity activity = activities.get(0);
		this.activityService.save(activity, conference.getId());
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveUnauthenticatedTest() {
		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAll());
		final Conference conference = conferences.get(1);
		conference.setIsDraft(true);
		final Date submission = new Date(116, 5, 3);
		final Date notification = new Date(117, 5, 3);
		final Date cameraReady = new Date(118, 5, 3);
		final Date startDate = new Date(119, 5, 3);
		final Date endDate = new Date(120, 5, 3);
		conference.setIsDraft(true);
		conference.setSubmission(submission);
		conference.setCameraReady(cameraReady);
		conference.setNotification(notification);
		conference.setStartDate(startDate);
		conference.setEndDate(endDate);
		final List<Activity> activities = new ArrayList<Activity>(this.activityService.findAll());
		final Activity activity = activities.get(0);
		this.activityService.save(activity, conference.getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveActivityWichNotBelongsToConferenceTest() {
		super.authenticate("admin1");
		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAll());
		final Conference conference = conferences.get(0);
		conference.setIsDraft(true);
		final Date submission = new Date(116, 5, 3);
		final Date notification = new Date(117, 5, 3);
		final Date cameraReady = new Date(118, 5, 3);
		final Date startDate = new Date(119, 5, 3);
		final Date endDate = new Date(120, 5, 3);
		conference.setIsDraft(true);
		conference.setSubmission(submission);
		conference.setCameraReady(cameraReady);
		conference.setNotification(notification);
		conference.setStartDate(startDate);
		conference.setEndDate(endDate);
		final List<Activity> activities = new ArrayList<Activity>(this.activityService.findAll());
		final Activity activity = activities.get(0);
		this.activityService.save(activity, conference.getId());
		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteWithConferenceInDraftModeTest() {
		super.authenticate("admin1");
		final List<Conference> conferences = new ArrayList<Conference>(this.conferenceService.findAll());
		final Conference conference = conferences.get(1);
		conference.setIsDraft(true);
		final Date submission = new Date(116, 5, 3);
		final Date notification = new Date(117, 5, 3);
		final Date cameraReady = new Date(118, 5, 3);
		final Date startDate = new Date(119, 5, 3);
		final Date endDate = new Date(120, 5, 3);
		conference.setIsDraft(false);
		conference.setSubmission(submission);
		conference.setCameraReady(cameraReady);
		conference.setNotification(notification);
		conference.setStartDate(startDate);
		conference.setEndDate(endDate);
		final List<Activity> activities = new ArrayList<Activity>(this.activityService.findAll());
		final Activity activity = activities.get(0);
		this.activityService.save(activity, conference.getId());
		super.unauthenticate();
	}

}
