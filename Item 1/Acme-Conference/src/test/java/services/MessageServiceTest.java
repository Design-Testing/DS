
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Actor;
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
	TopicService	topicService;

	@Autowired
	ActorService	actorService;

	@Autowired
	LoginService	loginService;


	@Test
	public void createTest() {
		super.authenticate("sponsor1");
		this.messageService.create();
		super.unauthenticate();
	}

	@Test
	public void sendTest() {
		super.authenticate("author1");
		final Message newMessage = this.messageService.create();
		final Actor actor = this.actorService.findOne(this.getEntityId("author1"));
		final Collection<Actor> rps = new ArrayList<>();
		rps.add(actor);
		newMessage.setRecivers(rps);
		newMessage.setBody("Body test");
		newMessage.setSubject("Subject test");
		newMessage.setTopic(this.topicService.findOne(this.getEntityId("topic1")));
		this.messageService.send(newMessage);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void sendUnauthenticatedTest() {
		final Message newMessage = this.messageService.findOne(this.getEntityId("message2"));
		this.messageService.send(newMessage);
	}

	//	@Test
	//	public void broadcastAuthorsTest() {
	//		super.authenticate("administrator2");
	//		final Message newMessage = new Message();
	//		newMessage.setBody("Body test");
	//		newMessage.setSubject("Subject test");
	//		newMessage.setTopic(this.topicService.findOne(this.getEntityId("topic1")));
	//		this.messageService.broadcastToAllAuthors(newMessage);
	//		super.unauthenticate();
	//	}
	//
	//	@Test
	//	public void broadcastActorsTest() {
	//		super.authenticate("administrator2");
	//		final Message newMessage = new Message();
	//		newMessage.setBody("Body test");
	//		newMessage.setSubject("Subject test");
	//		newMessage.setTopic(this.topicService.findOne(this.getEntityId("topic1")));
	//		this.messageService.broadcastToAllActors(newMessage);
	//		super.unauthenticate();
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void broadcastActorsWithoutAuthenticationTest() {
	//		final Message newMessage = this.messageService.create();
	//		newMessage.setBody("Body test");
	//		newMessage.setSubject("Subject test");
	//		newMessage.setTopic(this.topicService.findOne(this.getEntityId("topic1")));
	//		this.messageService.broadcastToAllActors(newMessage);
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void broadcastAuthorsWithoutAuthenticationTest() {
	//		final Message message = this.messageService.findOne(this.getEntityId("message1"));
	//		this.messageService.broadcastToAllAuthors(message);
	//	}

	@Test
	public void deleteTest() {
		super.authenticate("author1");
		final Message message = this.messageService.findOne(this.getEntityId("message1"));
		this.messageService.delete(message);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUnauthenticatedTest() {
		final Message message = this.messageService.findOne(this.getEntityId("message2"));
		this.messageService.delete(message);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNotOwnedTest() {
		super.authenticate("author1");
		final Message message = this.messageService.findOne(this.getEntityId("message2"));
		this.messageService.delete(message);
		super.unauthenticate();
	}
}
