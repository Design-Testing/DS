
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	@Autowired
	CommentService		commentService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	ActivityService		activityService;

	@Autowired
	ReportService		reportService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createOnConferenceTest() {
		this.commentService.create("conference", this.getEntityId("conference1"));
	}

	@Test
	public void create() {
		this.commentService.create();
	}

	@Test
	public void saveTest() {
		super.authenticate("author1");
		final Comment comment = this.commentService.create();
		comment.setText("Nuevo texto de prueba");
		comment.setTitle("Title");
		comment.setConference(this.conferenceService.findOne(this.getEntityId("conference5")));
		this.commentService.save(comment);
		super.unauthenticate();
	}
}
