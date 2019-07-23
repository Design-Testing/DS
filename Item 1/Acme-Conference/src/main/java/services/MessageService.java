
package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuthorService			authorService;


	public Message create() {
		final Message res = new Message();
		final Actor principal = this.actorService.findByPrincipal();
		res.setSender(principal);
		final Date moment = new Date(System.currentTimeMillis() - 1000);
		res.setMoment(moment);

		return res;

	}

	public Message findOne(final int messageId) {
		Assert.isTrue(messageId != 0);
		final Message result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);
		return result;
	}

	/**
	 * This method sends the message specified in the parameters (to the actors specified in the recipietns attribute of the message)
	 * and sets the logged user as the sender.
	 * The message specified in the parameters must have the following attributes: recipients, subject, body and topic
	 * */
	public Message send(final Message m) {
		Assert.notNull(m);
		Assert.isTrue(m.getId() == 0);

		final Actor sender = this.actorService.findByPrincipal();

		m.setSender(sender);
		final Date moment = new Date(System.currentTimeMillis() - 1000);
		m.setMoment(moment);

		final Message sent = this.messageRepository.save(m);

		return sent;
	}

	/**
	 * This method sends the message specified in the parameters to all the actors of the system and sets the logged user as the sender.
	 * The message specified in the parameters must have the following attributes: subject, body and topic
	 * */
	public void broadcastToAllActors(final Message m) {
		Assert.notNull(m);
		this.administratorService.findByPrincipal();

		final Collection<Actor> actors = this.actorService.findAll();
		final Actor actor = this.actorService.findByPrincipal();
		actors.remove(this.administratorService.findSystem());
		actors.remove(actor);
		m.setRecivers(actors);
		this.send(m);
	}

	/**
	 * This method sends the message specified in the parameters to all the authors of the system and sets the logged user as the sender.
	 * The message specified in the parameters must have the following attributes: subject, body and topic
	 * */
	public void broadcastToAllAuthors(final Message m) {
		Assert.notNull(m);
		this.administratorService.findByPrincipal();

		final Collection<Actor> actors = this.actorService.findAll();
		final Collection<Actor> autors = new ArrayList<Actor>();
		final Authority authAutor = new Authority();
		authAutor.setAuthority("AUTOR");
		for (final Actor actor : actors)
			if (actor.getUserAccount().getAuthorities().contains(authAutor))
				autors.add(actor);
		m.setRecivers(actors);
		this.send(m);
	}

	//TODO ALBA
	/**
	 * This method sends a message to the authors who have made a submission to a conference and sets the logged user as the sender.
	 * */
	public void broadcastToAuthorsSubmission() {
		final Message m = this.create();
		m.setSubject("New submission to a conference");
		m.setBody("You has sent a submission to a conference");
		this.administratorService.findByPrincipal();

		final Collection<Actor> actors = this.actorService.findAll();
		final Collection<Actor> autors = new ArrayList<Actor>();
		final Authority authAutor = new Authority();
		authAutor.setAuthority("AUTOR");
		for (final Actor actor : actors)
			if (actor.getUserAccount().getAuthorities().contains(authAutor))
				autors.add(actor);
		m.setRecivers(actors);
		this.send(m);
	}

	//TODO ALBA
	/**
	 * This method sends a message to the authors who has registered to a conference and sets the logged user as the sender.
	 * */
	public void broadcastToAuthorsRegistration() {
		final Message m = this.create();
		m.setSubject("New registration to a conference");
		m.setBody("Yo have been registered to a conference");
		this.administratorService.findByPrincipal();

		final Collection<Actor> actors = this.actorService.findAll();
		final Collection<Actor> autors = new ArrayList<Actor>();
		final Authority authAutor = new Authority();
		authAutor.setAuthority("AUTOR");
		for (final Actor actor : actors)
			if (actor.getUserAccount().getAuthorities().contains(authAutor))
				autors.add(actor);
		m.setRecivers(actors);
		this.send(m);
	}

	//TODO ALBA
	public void delete(final Message message) {

	}

	public Collection<Message> findAll() {
		final Collection<Message> res = this.messageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Collection<Message> findAllByTopic(final int topicId) {
		final Collection<Message> res = this.messageRepository.findAllByTopic(topicId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Message> findAllBySender(final int senderId) {
		final Collection<Message> res = this.messageRepository.findAllBySender(senderId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Message> findAllByRecipient(final int recipientId) {
		final Collection<Message> res = this.messageRepository.findAllByRecipient(recipientId);
		Assert.notNull(res);
		return res;
	}

}
