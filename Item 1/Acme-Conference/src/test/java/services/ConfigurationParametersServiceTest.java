
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationParametersServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@Test
	public void createTest() {

	}

	@Test
	public void saveTest() {

	}

	@Test
	public void findOneTest() {

	}

	@Test
	public void findAll() {

	}
}
