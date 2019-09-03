
package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
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
	private FolderService			folderService;

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
		final Actor principal = this.actorService.findByPrincipal();
		final Message result = this.messageRepository.findOne(messageId);
		Assert.isTrue(this.messageRepository.findByPrincipal(messageId, principal.getId()), "message.not.principal.error");
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

		final Actor sender = this.actorService.findByPrincipal();
		final Folder outbox = this.folderService.findOutboxByUserId(sender.getUserAccount().getId());
		final Collection<Message> outboxMessages = outbox.getMessages();

		m.setSender(sender);
		final Date moment = new Date(System.currentTimeMillis() - 1000);
		m.setMoment(moment);

		final Collection<Actor> recipients = m.getRecivers();
		Folder inbox;

		final Message sent = this.messageRepository.save(m);

		outboxMessages.add(sent);
		outbox.setMessages(outboxMessages);
		this.folderService.save(outbox, sender);

		for (final Actor r : recipients) {
			inbox = this.folderService.findInboxByUserId(r.getUserAccount().getId());
			final Collection<Message> inboxMessages = inbox.getMessages();
			inboxMessages.add(sent);
			inbox.setMessages(inboxMessages);
			this.folderService.save(inbox, r);
		}

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

		final Collection<Actor> autors = new ArrayList<Actor>();
		autors.addAll(this.authorService.findAll());
		m.setRecivers(autors);
		this.send(m);
	}

	/**
	 * This method sends a message to the authors who have made a submission to a conference and sets the logged user as the sender.
	 * */
	//	public void broadcastToAuthorsSubmission() {
	//		this.administratorService.findByPrincipal();
	//		final Message m = this.create();
	//		m.setSubject("New submission to a conference");
	//		m.setBody("You has sent a submission to a conference");
	//
	//		final Collection<Actor> actors = this.actorService.findAll();
	//		final Collection<Actor> autors = new ArrayList<Actor>();
	//		final Authority authAutor = new Authority();
	//		authAutor.setAuthority(Authority.AUTHOR);
	//		for (final Actor actor : actors)
	//			if (actor.getUserAccount().getAuthorities().contains(authAutor))
	//				autors.add(actor);
	//		m.setRecivers(autors);
	//		this.send(m);
	//	}

	/**
	 * This method sends a message to the authors who has registered to a conference and sets the logged user as the sender.
	 * */
	//	public void broadcastToAuthorsRegistration() {
	//		final Message m = this.create();
	//		m.setSubject("New registration to a conference");
	//		m.setBody("Yo have been registered to a conference");
	//		this.administratorService.findByPrincipal();
	//
	//		final Collection<Actor> actors = this.actorService.findAll();
	//		final Collection<Actor> autors = new ArrayList<Actor>();
	//		final Authority authAutor = new Authority();
	//		authAutor.setAuthority(Authority.AUTHOR);
	//		for (final Actor actor : actors)
	//			if (actor.getUserAccount().getAuthorities().contains(authAutor))
	//				autors.add(actor);
	//		m.setRecivers(autors);
	//		this.send(m);
	//	}

	public Collection<Message> findAllByFolderIdAndUserId(final int folderId, final int userAccountId) {
		Assert.isTrue(folderId != 0);
		Assert.isTrue(userAccountId != 0);

		return this.messageRepository.findAllByFolderIdAndUserId(folderId, userAccountId);
	}

	/**
	 * Remove a message from a folder
	 * */
	public void deleteFromFolder(final Message message, final Folder folder) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(folder.getMessages().contains(message));

		final Actor principal = this.actorService.findByPrincipal();

		final Collection<Message> folderMessages = this.findAllByFolderIdAndUserId(folder.getId(), principal.getUserAccount().getId());

		folderMessages.remove(message);
		folder.setMessages(folderMessages);
		this.folderService.save(folder, principal);

		//Delete message from db if only exists in one folder
		if (this.actorService.countByMessageId(message.getId()) == 0)
			this.messageRepository.delete(message);

	}

	public Collection<Message> findAll() {
		final Collection<Message> res = this.messageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Collection<Message> findAllByTopic(final int topicId) {
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Message> res = this.messageRepository.findAllByTopic(topicId, principal.getId());
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

	public Collection<Message> findAllByPrincipal(final int principalId) {
		Assert.isTrue(principalId != 0);
		final Collection<Message> res = this.messageRepository.findAllByPrincipal(principalId);
		Assert.notNull(res);
		return res;
	}

	public void delete(Message message) {
		final Actor principal = this.actorService.findByPrincipal();
		message = this.messageRepository.findOne(message.getId());
		final Folder inbox = this.folderService.findInboxByUserId(principal.getUserAccount().getId());
		final List<Message> messages = new ArrayList<>(inbox.getMessages());
		if (messages.contains(message))
			this.deleteFromFolder(message, this.folderService.findInboxByUserId(principal.getUserAccount().getId()));
		else
			this.deleteFromFolder(message, this.folderService.findOutboxByUserId(principal.getUserAccount().getId()));
	}

	public Collection<Message> findAllByRecipient(final int principalId, final int actorId) {
		final Collection<Message> res = this.messageRepository.findAllByRecipient(principalId, actorId);
		Assert.notNull(res);
		return res;
	}
}
