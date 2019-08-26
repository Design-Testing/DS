
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Topic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TopicServiceTest extends AbstractTest {

	@Autowired
	private TopicService	topicService;


	@Test
	public void create() {
		super.authenticate("admin1");
		final Topic t = this.topicService.create();
		Assert.notNull(t);
		Assert.isTrue(t.getId() == 0);

	}

	@Test
	public void save() {
		super.authenticate("admin1");
		Topic t = this.topicService.create();
		Assert.notNull(t);
		Assert.isTrue(t.getId() == 0);

		t.setEnglish("ENVENT");
		t.setSpanish("ENVENTO");

		t = this.topicService.save(t);
		Assert.isTrue(t.getId() != 0);
	}

	@Test
	public void detele() {
		super.authenticate("admin1");
		Topic t = this.topicService.create();
		Assert.notNull(t);
		Assert.isTrue(t.getId() == 0);

		t.setEnglish("ENVENT");
		t.setSpanish("ENVENTO");

		t = this.topicService.save(t);
		Assert.isTrue(t.getId() != 0);

		this.topicService.delete(t);

	}

}
