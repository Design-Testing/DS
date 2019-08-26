
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

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.HashPassword;
import domain.Actor;
import domain.Finder;
import domain.Reviewer;
import forms.ReviewerForm;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository	reviewerRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	public Reviewer create() {
		final Reviewer reviewer = new Reviewer();
		this.actorService.setAuthorityUserAccount(Authority.REVIEWER, reviewer);

		return reviewer;
	}

	public Reviewer findOne(final int reviewerId) {
		Assert.isTrue(reviewerId != 0);
		final Reviewer result = this.reviewerRepository.findOne(reviewerId);
		Assert.notNull(result);
		return result;
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);
		Reviewer result;

		if (reviewer.getId() == 0) {
			final String username = reviewer.getUserAccount().getUsername();
			final String password = HashPassword.hashPassword(reviewer.getUserAccount().getPassword());
			final Finder finder = this.finderService.createForNewActor();
			reviewer.setFinder(finder);
			this.actorService.setAuthorityUserAccount(Authority.REVIEWER, reviewer);
			reviewer.getUserAccount().setUsername(username);
			reviewer.getUserAccount().setPassword(password);
			result = this.reviewerRepository.save(reviewer);

		} else {
			final Actor principal = this.actorService.findByPrincipal();
			Assert.isTrue(principal.getId() == reviewer.getId(), "You only can edit your info");
			result = (Reviewer) this.actorService.save(reviewer);
		}
		return result;
	}

	public void delete(final Reviewer reviewer) {
		Assert.notNull(reviewer);
		Assert.isTrue(this.findByPrincipal().equals(reviewer));
		Assert.isTrue(reviewer.getId() != 0);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == reviewer.getId(), "You only can edit your info");
		Assert.isTrue(this.reviewerRepository.exists(reviewer.getId()));
		this.reviewerRepository.delete(reviewer);
	}

	/* ========================= OTHER METHODS =========================== */

	public Reviewer findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Reviewer reviewer = this.findByUserId(user.getId());
		Assert.notNull(reviewer);
		final boolean bool = this.actorService.checkAuthority(reviewer, Authority.REVIEWER);
		Assert.isTrue(bool);

		return reviewer;
	}

	public Reviewer findByUserId(final int id) {
		Assert.isTrue(id != 0);
		final Reviewer reviewer = this.reviewerRepository.findByUserId(id);
		return reviewer;
	}

	public void flush() {
		this.reviewerRepository.flush();
	}

	public Reviewer reconstruct(final ReviewerForm reviewerForm, final BindingResult binding) {
		Reviewer reviewer;

		if (reviewerForm.getId() == 0) {
			reviewer = this.create();
			reviewer.setName(reviewerForm.getName());
			reviewer.setSurname(reviewerForm.getSurname());
			reviewer.setPhoto(reviewerForm.getPhoto());
			reviewer.setPhone(reviewerForm.getPhone());
			reviewer.setEmail(reviewerForm.getEmail());
			reviewer.setKeywords(reviewerForm.getKeywords());
			reviewer.setAddress(reviewerForm.getAddress());
			reviewer.setVersion(reviewerForm.getVersion());
			reviewer.setFinder(this.finderService.create());
			final UserAccount account = this.userAccountService.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.REVIEWER);
			authorities.add(auth);
			account.setAuthorities(authorities);
			account.setUsername(reviewerForm.getUserAccountuser());
			account.setPassword(reviewerForm.getUserAccountpassword());
			reviewer.setUserAccount(account);
		} else {
			reviewer = this.reviewerRepository.findOne(reviewerForm.getId());
			reviewer.setName(reviewerForm.getName());
			reviewer.setSurname(reviewerForm.getSurname());
			reviewer.setPhoto(reviewerForm.getPhoto());
			reviewer.setPhone(reviewerForm.getPhone());
			reviewer.setEmail(reviewerForm.getEmail());
			reviewer.setKeywords(reviewerForm.getKeywords());
			reviewer.setAddress(reviewerForm.getAddress());
			reviewer.setVersion(reviewerForm.getVersion());
			reviewer.setFinder(this.finderService.findActorFinder());
			final UserAccount account = this.userAccountService.findOne(reviewer.getUserAccount().getId());
			account.setUsername(reviewerForm.getUserAccountuser());
			account.setPassword(reviewerForm.getUserAccountpassword());
			reviewer.setUserAccount(account);
		}

		this.validator.validate(reviewer, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return reviewer;
	}

	public Collection<Reviewer> findAll() {
		final Collection<Reviewer> result = this.reviewerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Collection<Reviewer> findReviewersAccordingToConference(final int conferenceId) {
		final Collection<Reviewer> result = this.reviewerRepository.findReviewersAccordingToConference(conferenceId);
		return result;
	}
}
