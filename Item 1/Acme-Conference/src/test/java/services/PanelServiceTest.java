
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Panel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PanelServiceTest extends AbstractTest {

	@Autowired
	PanelService	panelService;

	@Autowired
	LoginService	loginService;


	@Test
	public void createPanelWithAuthenticationTest() {
		super.authenticate("admin1");
		final Panel panel = this.panelService.create();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPanelWithoutAuthenticationTest() {
		final Panel panel = this.panelService.create();
	}
}
