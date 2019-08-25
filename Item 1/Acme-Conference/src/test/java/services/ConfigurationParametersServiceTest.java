
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.ConfigurationParameters;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationParametersServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	@Test(expected = IllegalArgumentException.class)
	public void saveCountryPhoneCodeTest() {
		super.authenticate("admin1");
		final ConfigurationParameters configurationParameters = this.configurationParametersService.find();
		configurationParameters.setCountryPhoneCode("34");
		this.configurationParametersService.save(configurationParameters);

	}

	@Test(expected = IllegalArgumentException.class)
	public void saveCreditCardMakeTest() {
		super.authenticate("admin1");
		final ConfigurationParameters configurationParameters = this.configurationParametersService.find();
		configurationParameters.getCreditCardMake().add("");
		this.configurationParametersService.save(configurationParameters);

	}

	@Test(expected = IllegalArgumentException.class)
	public void saveBannerTest() {
		super.authenticate("admin1");
		final ConfigurationParameters configurationParameters = this.configurationParametersService.find();
		configurationParameters.setBanner("prueba");
		this.configurationParametersService.save(configurationParameters);

	}

}
