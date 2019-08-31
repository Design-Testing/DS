
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
		final Comment comment = this.commentService.create("conference", 3204);
	}

	@Test
	public void create() {
		final Comment comment = this.commentService.create();
	}

	@Test
	public void saveTest() {
		final Comment comment = this.commentService.findOne(3177);
		this.commentService.save(comment);
	}
}
