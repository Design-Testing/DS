
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
	public void createSectionWithAuthenticationTest() {
		super.authenticate("admin1");
		final Section section = this.sectionService.create();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createSectionWithoutAuthenticationTest() {
		final Section section = this.sectionService.create();
	}

	@Test
	public void saveTest() {
		super.authenticate("admin1");
		final List<Tutorial> tutorials = new ArrayList<Tutorial>(this.tutorialService.findTutorials());
		final List<Section> sections = new ArrayList<Section>(this.sectionService.findAll());
		final Tutorial tutorial = tutorials.get(0);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		conference.setIsDraft(true);
		final Section section = sections.get(0);
		this.sectionService.save(section, tutorial.getId());
	}

	@Test
	public void deleteTest() {
		super.authenticate("admin1");
		final List<Tutorial> tutorials = new ArrayList<Tutorial>(this.tutorialService.findTutorials());
		final List<Section> sections = new ArrayList<Section>(this.sectionService.findAll());
		final Tutorial tutorial = tutorials.get(0);
		final Conference conference = this.conferenceService.findConferenceByTutorialId(tutorial.getId());
		final Section section = sections.get(0);
		conference.setIsDraft(true);
		this.sectionService.delete(section, tutorial.getId(), conference.getId());
	}
}
