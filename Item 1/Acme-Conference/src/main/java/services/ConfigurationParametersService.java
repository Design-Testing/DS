
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationParametersRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.ConfigurationParameters;

@Service
@Transactional
public class ConfigurationParametersService {

	@Autowired
	private ConfigurationParametersRepository	configurationParametersRepository;

	@Autowired
	private AdministratorService				administratorService;

	@Autowired
	private ActorService						actorService;


	public ConfigurationParameters create() {
		final Actor principal = this.administratorService.findByPrincipal();
		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		Assert.isTrue(isAdmin);
		return new ConfigurationParameters();
	}

	public Collection<ConfigurationParameters> findAll() {
		final Actor principal = this.administratorService.findByPrincipal();
		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		Assert.isTrue(isAdmin);
		final Collection<ConfigurationParameters> res = this.configurationParametersRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public ConfigurationParameters findOne(final int id) {
		final Actor principal = this.administratorService.findByPrincipal();
		final Boolean isAdmin = this.actorService.checkAuthority(principal, Authority.ADMIN);
		Assert.isTrue(isAdmin);
		Assert.isTrue(id != 0);
		final ConfigurationParameters res = this.configurationParametersRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public String findWelcomeMessageEsp() {
		return this.configurationParametersRepository.findWelcomeMessageEsp();
	}

	public String findWelcomeMessageEn() {
		return this.configurationParametersRepository.findWelcomeMessageEn();
	}

	public String findBanner() {
		return this.configurationParametersRepository.findBanner();
	}

	public String findSysName() {
		return this.configurationParametersRepository.findSysName();
	}

	public Collection<String> findMakes() {
		return this.configurationParametersRepository.findMakes();
	}

	public ConfigurationParameters find() {
		final ConfigurationParameters res = (ConfigurationParameters) this.configurationParametersRepository.findAll().toArray()[0];
		Assert.notNull(res);
		return res;
	}

	public ConfigurationParameters save(final ConfigurationParameters c) {
		Assert.notNull(c);
		final Administrator a = this.administratorService.findByPrincipal();
		final Boolean isAdmin = this.actorService.checkAuthority(a, Authority.ADMIN);
		Assert.isTrue(isAdmin);

		final ConfigurationParameters result = this.configurationParametersRepository.save(c);
		Assert.notNull(result);
		return result;

	}

	public void flush() {
		this.configurationParametersRepository.flush();
	}
}
