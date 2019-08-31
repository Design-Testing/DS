
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
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;


	@Test
	public void createTest() {
		final Sponsorship sponsorship = this.sponsorshipService.create();

	}

	@Test
	public void saveTest() {
		super.authenticate("sponsor1");
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(this.sponsorshipService.findAll());
		final Sponsorship sponsorship = sponsorships.get(0);
		sponsorship.getCreditCard().setExpirationMonth(12);
		sponsorship.setBanner("http://www.url.com/prueba");
		this.sponsorshipService.save(sponsorship);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveWithExpiredCreditCardTest() {
		super.authenticate("sponsor1");
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(this.sponsorshipService.findAll());
		final Sponsorship sponsorship = sponsorships.get(0);
		sponsorship.getCreditCard().setExpirationYear(10);
		sponsorship.setBanner("http://www.url.com/prueba");
		this.sponsorshipService.save(sponsorship);
		super.unauthenticate();
	}
	@Test(expected = IllegalArgumentException.class)
	public void saveSponsorshipNotOwnedTest() {
		super.authenticate("sponsor1");
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(this.sponsorshipService.findAll());
		final Sponsorship sponsorship = sponsorships.get(1);
		sponsorship.setBanner("http://www.url.com/prueba");
		this.sponsorshipService.save(sponsorship);
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		super.authenticate("sponsor1");
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(this.sponsorshipService.findAll());
		final Sponsorship sponsorship = sponsorships.get(0);
		this.sponsorshipService.delete(sponsorship);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteSponsorshipNotOwnedTest() {
		super.authenticate("sponsor1");
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(this.sponsorshipService.findAll());
		final Sponsorship sponsorship = sponsorships.get(1);
		this.sponsorshipService.delete(sponsorship);
		super.unauthenticate();
	}

}
