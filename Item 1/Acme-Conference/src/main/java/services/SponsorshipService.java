
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	//@Autowired
	//private MessageService			messageService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private RegistrationService		registrationService;


	//Constructor
	public SponsorshipService() {
		super();
	}

	//Simple CRUD method
	public Sponsorship create() {
		return new Sponsorship();
	}

	public Collection<Sponsorship> findAll() {
		final Collection<Sponsorship> res = this.sponsorshipRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);
		final Sponsorship res = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(res);
		return res;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));
		final Sponsor principal = this.sponsorService.findByPrincipal();
		Assert.isTrue(principal.getId() == sponsorship.getSponsor().getId(), "you can not delte a sponsorship that is not yours");
		this.sponsorshipRepository.delete(sponsorship);
	}

	public Double[] getStatisticsOfSponsorshipPerSponsor() {
		return this.sponsorshipRepository.getStatisticsOfSponsorshipPerSponsor();
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	public Collection<Sponsorship> findAllBySponsor() {
		final Sponsor principal = this.sponsorService.findByPrincipal();
		final Collection<Sponsorship> res = this.findAllByUserId(principal.getUserAccount().getId());
		Assert.notNull(res);
		return res;
	}

	public Sponsorship save(final Sponsorship s) {
		Assert.notNull(s);

		final Sponsor principal = this.sponsorService.findByPrincipal();
		final Collection<Sponsorship> ss = this.findAllByUserId(principal.getUserAccount().getId());
		Assert.isTrue(s.getSponsor().equals(principal));
		final CreditCard c = s.getCreditCard();

		if (s.getId() == 0) {
			s.setSponsor(principal);
			//Assert.isTrue(this.sponsorshipRepository.availableSponsorshipPosition(s.getPosition().getId(), principal.getUserAccount().getId()), "Cannot sponsors twice the same parade");
			Assert.notNull(c, "sponsorship.not.creditcard.error");
			//Assert.isTrue(this.positionService.exists(s.getPosition().getId()), "You must sponsors to a parade of the system");
			Assert.isTrue(!this.expiredCreditCard(c), "creditcard.expired.error");
		} else {
			Assert.isTrue(ss.contains(s), "sponsorship.ss.error");
			Assert.isTrue(!this.expiredCreditCard(s.getCreditCard()));
		}
		c.setNumber(c.getNumber().replaceAll(" ", ""));
		Assert.isTrue(this.registrationService.isValidInteger(c.getNumber()), "sponsorship.creditcard.number.error");
		final Sponsorship saved = this.sponsorshipRepository.save(s);
		return saved;
	}
	private Collection<Sponsorship> findAllByUserId(final int id) {
		Assert.isTrue(id != 0);
		return this.sponsorshipRepository.findAllByUserId(id);
	}

	public Sponsorship findRandomSponsorship(final int positionId) {
		Sponsorship res;
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAll();
		if (sponsorships.size() > 0) {
			final int randomNumber = (int) (Math.random() * sponsorships.size());
			res = (Sponsorship) sponsorships.toArray()[randomNumber];
			//TODO: sponsorship displayed notification
			//this.messageService.sponsorshipDisplayedMessage(res);
		} else
			res = null;

		return res;
	}

	public Sponsorship findOneSponsorship(final int sponsorshipId) {
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		final Sponsorship sponsorship = this.findOne(sponsorshipId);
		Assert.isTrue(sponsorship.getSponsor().getId() == sponsor.getId());
		return sponsorship;
	}

	// Ancilliary Method
	boolean expiredCreditCard(final CreditCard c) {
		boolean res = false;
		final Date now = new Date();
		final boolean mesCaducado = c.getExpirationMonth() < (now.getMonth() + 1);
		final boolean mismoAnyo = (c.getExpirationYear()) == (now.getYear() % 100);
		final boolean anyoCaducado = (c.getExpirationYear()) < (now.getYear() % 100);
		if (anyoCaducado || (mismoAnyo && mesCaducado))
			res = true;

		return res;
	}

	public Sponsorship findRandomSponsorship() {
		Sponsorship res;
		final Collection<Sponsorship> sponsorships = this.findAll();
		if (sponsorships.size() > 0) {
			final int randomNumber = (int) (Math.random() * sponsorships.size());
			res = (Sponsorship) sponsorships.toArray()[randomNumber];
		} else
			res = null;

		return res;
	}
}
