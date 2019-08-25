
package services;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationRepository;
import security.Authority;
import domain.Actor;
import domain.Author;
import domain.Conference;
import domain.CreditCard;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	@Autowired
	private RegistrationRepository	registrationRepository;


	public Registration create(final Author author, final Conference conference) {
		final Registration res = new Registration();
		Assert.notNull(author);
		Assert.notNull(conference);
		res.setAuthor(author);
		res.setConference(conference);

		return res;
	}

	public Registration findOne(final int registrationId) {
		Assert.isTrue(registrationId != 0);
		final Registration res = this.registrationRepository.findOne(registrationId);
		Assert.notNull(res);
		return res;
	}


	@Autowired
	private ActorService		actorService;

	private AuthorService		authorService;

	@Autowired
	private ConferenceService	conferenceService;


	public Collection<Registration> findByConference(final int conferenceId) {
		this.authorService.findByPrincipal();
		Assert.isTrue(conferenceId != 0);
		final Collection<Registration> registration = this.registrationRepository.findByConference(conferenceId);
		Assert.notNull(registration);
		return registration;
	}

	public Collection<Registration> findAll() {
		final Collection<Registration> res;

		final Actor principal = this.actorService.findByPrincipal();
		final boolean isAuthor = this.actorService.checkAuthority(principal, Authority.AUTHOR);
		final boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		Assert.isTrue(isAuthor || isAdmin);

		if (isAuthor)
			res = this.findAllByAuthorUserId(principal.getUserAccount().getId());
		else
			res = this.registrationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Registration save(final Registration registration) {
		Assert.notNull(registration);
		Registration saved;
		final Conference conference = registration.getConference();
		Assert.isTrue(this.conferenceService.exists(conference.getId()));
		final Author principal = this.authorService.findByPrincipal();

		if (registration.getId() == 0) {
			registration.setAuthor(principal);
			Assert.isTrue(this.validCreditCard(registration.getCreditCard()));
			saved = this.registrationRepository.save(registration);
			this.authorService.save(principal);
		} else {
			final Collection<Registration> authorRegistration = this.findAllByAuthorUserId(principal.getUserAccount().getId());
			Assert.isTrue(authorRegistration.contains(registration));
			saved = this.registrationRepository.save(registration);
		}

		return saved;
	}

	public void delete(final Registration registrationToDelete) {
		Assert.notNull(registrationToDelete);
		Assert.isTrue(registrationToDelete.getId() != 0);
		Assert.isTrue(this.registrationRepository.exists(registrationToDelete.getId()));
		final Author principal = this.authorService.findByPrincipal();
		final Collection<Registration> authorRegistration = this.findAllByAuthorUserId(principal.getUserAccount().getId());
		Assert.isTrue(authorRegistration.contains(registrationToDelete));
		this.registrationRepository.delete(registrationToDelete);
	}

	public void deleteAuthorRegistrations() {
		final Author c = this.authorService.findByPrincipal();
		final Collection<Registration> registrations = this.findAllByAuthorUserId(c.getUserAccount().getId());
		if (!registrations.isEmpty())
			this.deleteAll(registrations);
	}

	private void deleteAll(final Collection<Registration> registrationsToDelete) {
		this.registrationRepository.deleteInBatch(registrationsToDelete);
	}

	/**
	 * Valid a credit card date
	 * 
	 * @param creditCard
	 *            The credit card that you want to be valid
	 * @return True if it is a valid credit card and false if not
	 * @author a8081
	 * */
	private boolean validCreditCard(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		final LocalDate now = LocalDate.now();
		boolean result = creditCard.getExpirationYear() < now.getYear();
		if (creditCard.getExpirationYear() == now.getYear())
			result = creditCard.getExpirationMonth() > now.getMonthOfYear();
		return result;
	}

	private Collection<Registration> findAllByAuthorUserId(final int id) {
		Assert.isTrue(id != 0);
		Assert.isTrue(this.checkPrincipal(id));
		return this.registrationRepository.findAllByAuthorUserId(id);
	}

	private boolean checkPrincipal(final int id) {
		final Actor a = this.actorService.findByPrincipal();
		return a.getUserAccount().getId() == id;
	}

}
