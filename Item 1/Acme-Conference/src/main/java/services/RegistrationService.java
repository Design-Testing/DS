
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationRepository;
import domain.Author;
import domain.Conference;
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

	public Registration save(final Registration registration) {
		Assert.notNull(registration);
		Assert.notNull(registration.getCreditCard());
		final Registration res = this.registrationRepository.save(registration);
		Assert.notNull(res);
		return res;
	}

	public Registration findOne(final Integer registrationId) {
		Assert.notNull(registrationId);
		final Registration res = this.registrationRepository.findOne(registrationId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Registration> findAll() {
		final Collection<Registration> res = this.registrationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

}
