
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	@Autowired
	private FolderRepository	folderRepository;

	@Autowired
	private ActorService		actorService;


	public Folder create() {
		final Folder folder = new Folder();
		final Collection<Message> ms = new ArrayList<>();
		folder.setMessages(ms);
		return folder;
	}

	public Collection<Folder> findAll() {
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Folder> res = this.findAllByUserId(principal.getUserAccount().getId());
		Assert.notNull(res);

		return res;
	}

	public Folder findOne(final int id) {
		final Folder res = this.folderRepository.findOne(id);

		return res;
	}

	public Folder save(final Folder f, final Actor a) {
		Assert.notNull(f);
		Assert.notNull(a);

		Folder saved;

		if (f.getId() == 0) {
			f.setActor(a);
			saved = this.folderRepository.save(f);
		} else {
			final Collection<Folder> fs = this.findAllByUserId(a.getUserAccount().getId());
			Assert.isTrue(fs.contains(f));
			saved = this.folderRepository.save(f);
		}
		return saved;
	}

	public Collection<Folder> setFoldersByDefault(final Actor actor) {
		final Collection<Folder> folders = new ArrayList<Folder>();
		final Collection<Message> messages = new ArrayList<Message>();

		final Folder inbox = this.create();
		inbox.setName("In box");
		inbox.setActor(actor);
		inbox.setMessages(messages);
		this.save(inbox, actor);
		folders.add(inbox);

		final Folder outbox = this.create();
		outbox.setName("Out box");
		outbox.setActor(actor);
		outbox.setMessages(messages);
		this.save(outbox, actor);
		folders.add(outbox);

		return folders;
	}

	public Collection<Folder> saveAll(final Collection<Folder> fs) {
		Assert.notEmpty(fs);
		return this.folderRepository.save(fs);
	}

	public Collection<Folder> findAllByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findAllByUserId(id);
	}

	public Folder findInboxByUserId(final int id) {
		Assert.isTrue(id != 0);
		System.out.println("AQUIIIIIIII" + id);
		return this.folderRepository.findInboxByUserId(id);
	}

	public Folder findOutboxByUserId(final int id) {
		Assert.isTrue(id != 0);

		return this.folderRepository.findOutboxByUserId(id);
	}

	public Collection<Folder> findAllByMessageIdAndUserId(final int mid, final int uid) {
		Assert.isTrue(mid != 0);
		Assert.isTrue(uid != 0);

		return this.folderRepository.findAllByMessageIdAndUserId(mid, uid);
	}

	public void flush() {
		this.folderRepository.flush();

	}
}
