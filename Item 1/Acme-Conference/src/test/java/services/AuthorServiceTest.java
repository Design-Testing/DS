
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Author;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuthorServiceTest extends AbstractTest {

	@Autowired
	AuthorService	authorService;

	@Autowired
	LoginService	loginService;


	@Test
	public void createTest() {
		final Author author = this.authorService.create();
		Assert.notNull(author);
		Assert.notNull(author.getUserAccount());

	}

	@Test
	public void saveTest() {
		super.authenticate("author1");
		final List<Author> authors = new ArrayList<Author>(this.authorService.findAll());
		final Author author = authors.get(0);
		author.setEmail("nuevocorreo@correo.org");

		this.authorService.save(author);

	}

}
