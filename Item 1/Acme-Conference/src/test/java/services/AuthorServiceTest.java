
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
	}

	@Test
	public void saveTest() {
		super.authenticate("author1");
		final List<Author> authors = new ArrayList<Author>(this.authorService.findAll());
		final Author author = authors.get(0);
		author.setName("Test");
		this.authorService.save(author);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveUnauthenticatedTest() {
		final List<Author> authors = new ArrayList<Author>(this.authorService.findAll());
		final Author author = authors.get(0);
		author.setName("Test");
		this.authorService.save(author);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveAnotherAuthorTest() {
		super.authenticate("author2");
		final List<Author> authors = new ArrayList<Author>(this.authorService.findAll());
		final Author author = authors.get(0);
		author.setName("Test");
		this.authorService.save(author);
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		super.authenticate("author1");
		final List<Author> authors = new ArrayList<Author>(this.authorService.findAll());
		final Author author = authors.get(0);
		this.authorService.delete(author);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUnauthenticatedTest() {
		final List<Author> authors = new ArrayList<Author>(this.authorService.findAll());
		final Author author = authors.get(0);
		this.authorService.delete(author);
		super.unauthenticate();
	}

}
