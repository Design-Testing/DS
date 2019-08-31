
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	MessageService	messageService;

	@Autowired
	LoginService	loginService;


	@Test
	public void createTest() {
		super.authenticate("sponsor1");
		final Message message = this.messageService.create();
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createUnauthenticatedTest() {
		final Message message = this.messageService.create();
	}

	@Test
	public void sendTest() {
		super.authenticate("admin1");
		final Message message = this.messageService.findOne(3202);
		this.messageService.send(message);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void sendNotOwnedTest() {
		super.authenticate("sponsor1");
		final Message message = this.messageService.findOne(3202);
		this.messageService.send(message);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void sendUnauthenticatedTest() {
		final Message message = this.messageService.findOne(3202);
		this.messageService.send(message);
	}

	@Test
	public void broadcastAuthorsTest() {
		super.authenticate("admin1");
		final Message message = this.messageService.findOne(3202);
		this.messageService.broadcastToAllAuthors(message);
		super.unauthenticate();
	}

	@Test
	public void broadcastActorsTest() {
		super.authenticate("admin1");
		final Message message = this.messageService.findOne(3202);
		this.messageService.broadcastToAllActors(message);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void broadcastActorsWithoutAuthenticationTest() {
		final Message message = this.messageService.findOne(3202);
		this.messageService.broadcastToAllActors(message);
	}

	@Test(expected = IllegalArgumentException.class)
	public void broadcastAuthorsWithoutAuthenticationTest() {
		final Message message = this.messageService.findOne(3202);
		this.messageService.broadcastToAllAuthors(message);
	}

	@Test
	public void broadcastAuthorsWithSubmissionTest() {
		super.authenticate("admin1");
		this.messageService.broadcastToAuthorsSubmission();
		super.unauthenticate();
	}

	@Test
	public void broadcastAuthorsWithRegistration() {
		super.authenticate("admin1");
		this.messageService.broadcastToAuthorsRegistration();
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		super.authenticate("author2");
		final Message message = this.messageService.findOne(3203);
		this.messageService.delete(message);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUnauthenticatedTest() {
		final Message message = this.messageService.findOne(3203);
		this.messageService.delete(message);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNotOwnedTest() {
		super.authenticate("author1");
		final Message message = this.messageService.findOne(3203);
		this.messageService.delete(message);
		super.unauthenticate();
	}
}
