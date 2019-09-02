
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	@Autowired
	SectionService		sectionService;

	@Autowired
	TutorialService		tutorialService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createTest() {
		super.authenticate("admin1");
		final Section section = this.sectionService.create();
		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		super.authenticate("admin1");
		final int tutorialId = this.getEntityId("tutorial1");
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		conference.setIsDraft(true);
		final int sectionId = this.getEntityId("section1");
		final Section section = this.sectionService.findOne(sectionId);
		this.sectionService.save(section, tutorial.getId());
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveUnauthenticatedTest() {
		final int tutorialId = this.getEntityId("tutorial1");
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		conference.setIsDraft(true);
		final int sectionId = this.getEntityId("section1");
		final Section section = this.sectionService.findOne(sectionId);
		this.sectionService.save(section, tutorial.getId());
	}

	@Test
	public void deleteTest() {
		super.authenticate("admin1");
		final int tutorialId = this.getEntityId("tutorial1");
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		final int sectionId = this.getEntityId("section1");
		final Section section = this.sectionService.findOne(sectionId);
		conference.setIsDraft(true);
		this.sectionService.delete(section, tutorial.getId(), conference.getId());
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUnauthenticatedTest() {
		final int tutorialId = this.getEntityId("tutorial1");
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		final int sectionId = this.getEntityId("section1");
		final Section section = this.sectionService.findOne(sectionId);
		conference.setIsDraft(true);
		this.sectionService.delete(section, tutorial.getId(), conference.getId());
	}
}
