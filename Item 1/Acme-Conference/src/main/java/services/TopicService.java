
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
		Assert.notNull(principal, "Just an administrator can manage topics");
		Assert.notNull(topic.getEnglish(), "An english name must be provided");
		Assert.notNull(topic.getSpanish(), "A spanish name must be provided");
		Assert.isTrue(!topic.getEnglish().equals(""), "An english name must be provided");
		Assert.isTrue(!topic.getSpanish().equals(""), "A spanish name must be provided");
		final Topic res = this.topicRepository.save(topic);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Topic topic) {
		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal, "Just an administrator can manage topics");
		//No se puede eliminar un topic si hay un mensaje con ese topic
		final Collection<Message> messages = this.messageService.findAllByTopic(topic.getId());
		Assert.isTrue(messages.isEmpty(), "To delete a topic there must not be any message related to it");
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
