
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository		actorRepository;

	@Autowired
	private UserAccountService	userAccountService;


	public Actor create() {
		return new Actor();
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> result = this.actorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Actor findOne(final int id) {
		Assert.isTrue(id != 0);
		final Actor result = this.actorRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Actor findByUserId(final int id) {
		Assert.isTrue(id != 0);
		return this.actorRepository.findByUserId(id);
	}

	public int countByMessageId(final Integer id) {
		Assert.isTrue(id != 0);
		return this.actorRepository.countByMessageId(id);
	}

	public Actor findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user, "User account principal cannot be null");

		final Actor a = this.findByUserId(user.getId());
		Assert.notNull(a, "Principal actor cannot be null");

		return a;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);

		final Actor principal = this.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(actor.equals(principal));

		Actor result;
		result = this.actorRepository.save(actor);
		Assert.notNull(result);

		return result;
	}

	/* Inicializa una cuenta de usuario y autoridad de un nuevo actor */
	public Actor setAuthorityUserAccount(final String authority, final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() == 0);
		final UserAccount userAccount = this.userAccountService.create();
		final Collection<Authority> authorities = new ArrayList<>();
		final Authority auth = new Authority();
		auth.setAuthority(authority);

		if (!authorities.contains(auth))
			authorities.add(auth);
		userAccount.setAuthorities(authorities);
		final UserAccount saved = this.userAccountService.save(userAccount);
		actor.setUserAccount(saved);

		//this.folderService.setFoldersByDefault(actor); it references to an actor that not exists

		return actor;
	}

	/* Comprueba si la cuenat de usuario de un actor contiene una autoridad específica */
	public boolean checkAuthority(final Actor a, final String auth) {
		Assert.notNull(auth);

		final UserAccount user = a.getUserAccount();
		final Actor retrieved = this.findByUserId(user.getId());
		Assert.notNull(retrieved);

		final Collection<Authority> auths = user.getAuthorities();
		Assert.notEmpty(auths);

		final Authority banned = new Authority();
		Assert.isTrue(!auths.contains(banned));

		final Authority newAuth = new Authority();
		newAuth.setAuthority(auth);

		return auths.contains(newAuth);
	}

}
