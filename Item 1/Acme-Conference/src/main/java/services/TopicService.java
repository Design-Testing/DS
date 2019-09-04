
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TopicRepository;
import domain.Administrator;
import domain.Message;
import domain.Topic;

@Service
@Transactional
public class TopicService {

	@Autowired
	private TopicRepository			topicRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageService			messageService;


	public Topic create() {
		final Topic res = new Topic();
		res.setEnglish("");
		res.setSpanish("");
		return res;

	}

	public Topic findOne(final int topicId) {
		Assert.isTrue(topicId != 0);
		final Topic result = this.topicRepository.findOne(topicId);
		Assert.notNull(result);
		return result;
	}

	public Topic save(final Topic topic) {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal, "error.a");
		Assert.notNull(topic.getEnglish(), "error.b");
		Assert.notNull(topic.getSpanish(), "error.c");
		Assert.isTrue(!topic.getEnglish().equals(""), "error.d");
		Assert.isTrue(!topic.getSpanish().equals(""), "error.e");
		final Topic res = this.topicRepository.save(topic);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Topic topic) {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal, "error.g");
		//No se puede eliminar un topic si hay un mensaje con ese topic
		final Collection<Message> messages = this.messageService.findAllByTopic(topic.getId());
		Assert.isTrue(messages.isEmpty(), "error.h");
		this.topicRepository.delete(topic);
	}

	public Collection<Topic> findAll() {
		final Collection<Topic> res = this.topicRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Collection<Topic> findTopicByNames(final String spanishName, final String englishName) {
		final Collection<Topic> result = this.topicRepository.findTopicByNames(spanishName, englishName);
		Assert.notNull(result);
		return result;
	}

}
