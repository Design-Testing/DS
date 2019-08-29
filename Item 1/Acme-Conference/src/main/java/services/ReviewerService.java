
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
import forms.ActorForm;

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

	@Autowired
	private FolderService		folderService;


	public Reviewer create() {
		final Reviewer reviewer = new Reviewer();
		return reviewer;
	}

	public Reviewer findOne(final int reviewerId) {
		Assert.isTrue(reviewerId != 0);
		final Reviewer result = this.reviewerRepository.findOne(reviewerId);
		Assert.notNull(result);
		return result;
	}

	public Boolean checkForEmailInUse(final String email) {
		Boolean res = false;
		final String inUse = this.reviewerRepository.checkForEmailInUse(email);
		if (inUse != null)
			res = true;
		return res;
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);
		Reviewer result;

		if (reviewer.getId() == 0) {
			final String username = reviewer.getUserAccount().getUsername();
			final String password = HashPassword.hashPassword(reviewer.getUserAccount().getPassword());
			final Finder finder = this.finderService.createForNewActor();
			reviewer.setFinder(finder);
			final Reviewer withUserAccount = (Reviewer) this.actorService.setUserAccount(Authority.AUTHOR, reviewer, username, password);
			Assert.isTrue(this.checkForEmailInUse(withUserAccount.getEmail()) == false, "Email is already in use");
			result = this.reviewerRepository.save(withUserAccount);

			this.folderService.setFoldersByDefault(result);

		} else {
			final String password = HashPassword.hashPassword(reviewer.getUserAccount().getPassword());
			final Actor principal = this.actorService.findByPrincipal();
			reviewer.getUserAccount().setPassword(password);
			Assert.isTrue(principal.getId() == reviewer.getId(), "You only can edit your info");
			result = this.reviewerRepository.save(reviewer);
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

	public Collection<Reviewer> findAll() {
		final Collection<Reviewer> res = this.reviewerRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	/* ========================= OTHER METHODS =========================== */

	public Reviewer findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Reviewer reviewer = this.findByUserId(user.getId());
		Assert.notNull(reviewer);
		final boolean bool = this.actorService.checkAuthority(reviewer, Authority.AUTHOR);
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

	public Reviewer reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Reviewer reviewer;

		if (actorForm.getId() == 0) {
			reviewer = this.create();
			reviewer.setName(actorForm.getName());
			reviewer.setSurname(actorForm.getSurname());
			reviewer.setMiddleName(actorForm.getMiddleName());
			reviewer.setPhoto(actorForm.getPhoto());
			reviewer.setPhone(actorForm.getPhone());
			Assert.isTrue(this.checkForEmailInUse(actorForm.getEmail()) == false, "Email is already in use");
			reviewer.setEmail(actorForm.getEmail());
			reviewer.setAddress(actorForm.getAddress());
			reviewer.setVersion(actorForm.getVersion());
			reviewer.setFinder(this.finderService.create());
			//			reviewer.setScore(0.0);
			//			reviewer.setSpammer(false);
			final UserAccount account = this.userAccountService.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.AUTHOR);
			authorities.add(auth);
			account.setAuthorities(authorities);
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			reviewer.setUserAccount(account);
		} else {
			reviewer = this.reviewerRepository.findOne(actorForm.getId());
			reviewer.setName(actorForm.getName());
			reviewer.setSurname(actorForm.getSurname());
			reviewer.setPhoto(actorForm.getPhoto());
			reviewer.setPhone(actorForm.getPhone());
			Assert.isTrue(this.checkForEmailInUse(actorForm.getEmail()) == false, "Email is already in use");
			reviewer.setEmail(actorForm.getEmail());
			reviewer.setAddress(actorForm.getAddress());
			reviewer.setVersion(actorForm.getVersion());
			reviewer.setFinder(this.finderService.findActorFinder());
			final UserAccount account = this.userAccountService.findOne(reviewer.getUserAccount().getId());
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			reviewer.setUserAccount(account);
		}

		this.validator.validate(reviewer, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return reviewer;
	}

	public Collection<Reviewer> findReviewersAssignedToSubmission(final int submissionId) {
		final Collection<Reviewer> result = this.reviewerRepository.findReviewersAssignedToSubmission(submissionId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Reviewer> findReviewersAccordingToConference(final int conferenceId) {
		final Collection<Reviewer> result = this.reviewerRepository.findReviewersAccordingToConference(conferenceId);
		return result;
	}

}
