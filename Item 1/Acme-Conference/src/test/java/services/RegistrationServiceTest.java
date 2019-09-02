
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Author;
import domain.Conference;
import domain.Registration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RegistrationServiceTest extends AbstractTest {

	@Autowired
	RegistrationService	registrationService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	AuthorService		authorService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createTest() {
		final int authorId = this.getEntityId("author1");
		final Author author = this.authorService.findOne(authorId);
		final int conferenceId = this.getEntityId("conference1");
		final Conference conference = this.conferenceService.findOne(conferenceId);
		final Registration registration = this.registrationService.create(author, conference);
		super.unauthenticate();
	}

	@Test
	public void saveTest() {
		final int authorId = this.getEntityId("author1");
		final Author author = this.authorService.findOne(authorId);
		super.authenticate(author.getUserAccount().getUsername());
		final int registrationId = this.getEntityId("registration1");
		final Registration registration = this.registrationService.findOne(registrationId);
		registration.getCreditCard().setExpirationMonth(12);
		this.registrationService.save(registration);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveUnauthenticatedTest() {
		final int registrationId = this.getEntityId("registration1");
		final Registration registration = this.registrationService.findOne(registrationId);
		registration.getCreditCard().setExpirationMonth(12);
		this.registrationService.save(registration);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWithExpiredCreditCardTest() {
		final int authorId = this.getEntityId("author1");
		final Author author = this.authorService.findOne(authorId);
		super.authenticate(author.getUserAccount().getUsername());
		final int registrationId = this.getEntityId("registration1");
		final Registration registration = this.registrationService.findOne(registrationId);
		this.registrationService.save(registration);
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		final int authorId = this.getEntityId("author1");
		final Author author = this.authorService.findOne(authorId);
		super.authenticate(author.getUserAccount().getUsername());
		final int registrationId = this.getEntityId("registration1");
		final Registration registration = this.registrationService.findOne(registrationId);
		this.registrationService.delete(registration);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNotOwnedTest() {
		final int authorId = this.getEntityId("author1");
		final Author author = this.authorService.findOne(authorId);
		super.authenticate(author.getUserAccount().getUsername());
		final int registrationId = this.getEntityId("registration2");
		final Registration registration = this.registrationService.findOne(registrationId);
		this.registrationService.delete(registration);
		super.unauthenticate();
	}

	@Test
	public void deleteAuthorRegistrations() {
		final int authorId = this.getEntityId("author1");
		final Author author = this.authorService.findOne(authorId);
		super.authenticate(author.getUserAccount().getUsername());
		this.registrationService.deleteAuthorRegistrations();
		super.unauthenticate();

	}
}
