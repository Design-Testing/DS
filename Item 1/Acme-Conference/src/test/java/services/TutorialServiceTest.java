
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
import domain.Conference;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	@Autowired
	TutorialService		tutorialService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createTutorialWithAuthenticationTest() {
		super.authenticate("admin1");
		final Tutorial tutorial = this.tutorialService.create();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTutorialWithoutAuthenticationTest() {
		final Tutorial tutorial = this.tutorialService.create();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteWithConferenceNotInDraftModeTest() {
		final List<Tutorial> tutorials = new ArrayList<Tutorial>(this.tutorialService.findTutorials());
		final Tutorial tutorial = tutorials.get(0);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		this.tutorialService.delete(tutorial, conference.getId());
	}

	@Test
	public void deleteWithConferenceInDraftModeTest() {
		final List<Tutorial> tutorials = new ArrayList<Tutorial>(this.tutorialService.findTutorials());
		final Tutorial tutorial = tutorials.get(0);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
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
		final Conference saved = this.conferenceService.save(conference);
		this.tutorialService.delete(tutorial, saved.getId());
	}
}
