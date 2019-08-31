
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void createTest() {
		final Sponsor sponsor = this.sponsorService.create();
	}

	@Test
	public void saveTest() {
		super.authenticate("sponsor1");
		final List<Sponsor> sponsors = new ArrayList<Sponsor>(this.sponsorService.findAll());
		final Sponsor sponsor = sponsors.get(0);
		sponsor.setEmail("probando@correo.com");
		this.sponsorService.save(sponsor);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveOtherSponsorTest() {
		super.authenticate("sponsor1");
		final List<Sponsor> sponsors = new ArrayList<Sponsor>(this.sponsorService.findAll());
		final Sponsor sponsor = sponsors.get(1);
		sponsor.setName("Pruebas");
		this.sponsorService.save(sponsor);
		super.unauthenticate();
	}
}
