
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.HashPassword;
import domain.Actor;
import domain.Finder;
import domain.Sponsor;
import forms.ActorForm;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository	sponsorRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private FinderService		finderService;


	public Sponsor create() {
		final Sponsor s = new Sponsor();
		return s;
	}

	public Sponsor findByUserId(final int id) {
		Assert.isTrue(id != 0);
		return this.sponsorRepository.findByUserId(id);
	}

	public Sponsor findOne(final int id) {
		Assert.isTrue(id != 0);
		final Sponsor result = this.sponsorRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Sponsor> findAll() {

		final Collection<Sponsor> result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Sponsor s = this.findByUserId(user.getId());
		Assert.notNull(s);
		final boolean bool = this.actorService.checkAuthority(s, Authority.SPONSOR);
		Assert.isTrue(bool);

		return s;
	}

	public Boolean checkForEmailInUse(final String email) {
		Boolean res = false;
		final String inUse = this.sponsorRepository.checkForEmailInUse(email);
		if (inUse != null)
			res = true;
		return res;
	}
	public Sponsor save(final Sponsor s) {
		Assert.notNull(s);
		Sponsor result;
		if (s.getId() == 0) {
			final String username = s.getUserAccount().getUsername();
			final String password = HashPassword.hashPassword(s.getUserAccount().getPassword());
			final Finder finder = this.finderService.createForNewActor();
			s.setFinder(finder);
			final Sponsor withUserAccount = (Sponsor) this.actorService.setUserAccount(Authority.SPONSOR, s, username, password);
			Assert.isTrue(this.checkForEmailInUse(withUserAccount.getEmail()) == false, "Email is already in use");
			result = this.sponsorRepository.save(withUserAccount);
			this.folderService.setFoldersByDefault(result);

		} else {
			final String password = HashPassword.hashPassword(s.getUserAccount().getPassword());
			final Actor principal = this.actorService.findByPrincipal();
			s.getUserAccount().setPassword(password);
			Assert.isTrue(principal.getId() == s.getId(), "You only can edit your info");
			result = this.sponsorRepository.save(s);
		}
		return result;
	}

	public Sponsor reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Sponsor sponsor;
		if (actorForm.getId() == 0) {
			sponsor = this.create();
			final UserAccount account = this.userAccountService.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.SPONSOR);
			authorities.add(auth);
			account.setAuthorities(authorities);
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			sponsor.setUserAccount(account);
		} else {
			sponsor = this.sponsorRepository.findOne(actorForm.getId());
			final UserAccount account = this.userAccountService.findOne(sponsor.getUserAccount().getId());
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			sponsor.setUserAccount(account);
		}
		Assert.isTrue(this.checkForEmailInUse(actorForm.getEmail()) == false, "Email is already in use");
		sponsor.setName(actorForm.getName());
		sponsor.setMiddleName(actorForm.getMiddleName());
		sponsor.setSurname(actorForm.getSurname());
		sponsor.setPhoto(actorForm.getPhoto());
		sponsor.setPhone(actorForm.getPhone());
		sponsor.setEmail(actorForm.getEmail());
		sponsor.setAddress(actorForm.getAddress());

		this.validator.validate(sponsor, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return sponsor;
	}

	public void flush() {
		this.sponsorRepository.flush();
	}

}
