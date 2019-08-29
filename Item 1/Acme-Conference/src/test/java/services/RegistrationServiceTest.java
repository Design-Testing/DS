
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
		final Author author = this.authorService.findOne(3198);
		final Conference conference = this.conferenceService.findOne(3204);
		final Registration registration = this.registrationService.create(author, conference);
	}

	@Test
	public void saveTest() {

	}

	@Test
	public void deleteTest() {

	}

	@Test
	public void deleteAuthorRegistrations() {

	}

	@Test
	public void tarjetaCaducadaTest() {

	}

	@Test
	public void isValidInteger() {

	}
}
