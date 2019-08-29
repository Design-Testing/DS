
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Presentation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PresentationServiceTest extends AbstractTest {

	@Autowired
	PresentationService	presentationService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createPresentationWithAuthenticationTest() {
		super.authenticate("admin1");
		final Presentation presentation = this.presentationService.create();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPresentationWithoutAuthenticationTest() {
		final Presentation presentation = this.presentationService.create();
	}
}
