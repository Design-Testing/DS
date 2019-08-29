
package services.auxiliary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.AuthorService;
import services.FolderService;
import services.ReviewerService;
import services.SponsorService;
import services.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Reviewer;
import domain.Sponsor;
import forms.ActorForm;

@Service
@Transactional
public class RegisterService {

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private FolderService			folderService;


	public Administrator saveAdmin(final Administrator admin, final BindingResult binding) {
		Administrator result;
		final UserAccount ua = admin.getUserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(ua.getPassword(), null);
		if (admin.getId() == 0) {
			Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");
			ua.setPassword(hash);
			admin.setUserAccount(ua);
			result = this.administratorService.save(admin);
			UserAccount uaSaved = result.getUserAccount();
			uaSaved.setAuthorities(ua.getAuthorities());
			uaSaved.setUsername(ua.getUsername());
			uaSaved.setPassword(ua.getPassword());
			uaSaved = this.userAccountService.save(uaSaved);
			result.setUserAccount(uaSaved);
			this.folderService.setFoldersByDefault(result);
		} else {
			final Administrator old = this.administratorService.findOne(admin.getId());

			ua.setPassword(hash);
			if (!old.getUserAccount().getUsername().equals(ua.getUsername()))
				Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");

			result = this.administratorService.save(admin);

		}

		return result;
	}

	public Sponsor saveSponsor(final Sponsor sponsor, final BindingResult binding) {
		Sponsor result;
		final UserAccount ua = sponsor.getUserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		this.sponsorService.findByPrincipal();
		final String hash = encoder.encodePassword(ua.getPassword(), null);
		if (sponsor.getId() == 0) {
			Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");
			ua.setPassword(hash);
			sponsor.setUserAccount(ua);
			result = this.sponsorService.save(sponsor);
			UserAccount uaSaved = result.getUserAccount();
			uaSaved.setAuthorities(ua.getAuthorities());
			uaSaved.setUsername(ua.getUsername());
			uaSaved.setPassword(ua.getPassword());
			uaSaved = this.userAccountService.save(uaSaved);
			result.setUserAccount(uaSaved);
			this.folderService.setFoldersByDefault(result);
		} else {
			final Sponsor old = this.sponsorService.findOne(sponsor.getId());

			ua.setPassword(hash);
			if (!old.getUserAccount().getUsername().equals(ua.getUsername()))
				Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");

			result = this.sponsorService.save(sponsor);

		}

		return result;
	}

	public Reviewer saveReviewer(final Reviewer reviewer, final BindingResult binding) {
		Reviewer result;
		final UserAccount ua = reviewer.getUserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(ua.getPassword(), null);
		if (reviewer.getId() == 0) {
			Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");
			ua.setPassword(hash);
			reviewer.setUserAccount(ua);
			result = this.reviewerService.save(reviewer);
			UserAccount uaSaved = result.getUserAccount();
			uaSaved.setAuthorities(ua.getAuthorities());
			uaSaved.setUsername(ua.getUsername());
			uaSaved.setPassword(ua.getPassword());
			uaSaved = this.userAccountService.save(uaSaved);
			result.setUserAccount(uaSaved);
			this.folderService.setFoldersByDefault(result);
		} else {
			final Reviewer old = this.reviewerService.findOne(reviewer.getId());

			ua.setPassword(hash);
			if (!old.getUserAccount().getUsername().equals(ua.getUsername()))
				Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");

			result = this.reviewerService.save(reviewer);

		}

		return result;
	}

	public Author saveAuthor(final Author author, final BindingResult binding) {
		Author result;
		final UserAccount ua = author.getUserAccount();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		this.authorService.findByPrincipal();
		final String hash = encoder.encodePassword(ua.getPassword(), null);
		if (author.getId() == 0) {
			Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");
			ua.setPassword(hash);
			author.setUserAccount(ua);
			result = this.authorService.save(author);
			UserAccount uaSaved = result.getUserAccount();
			uaSaved.setAuthorities(ua.getAuthorities());
			uaSaved.setUsername(ua.getUsername());
			uaSaved.setPassword(ua.getPassword());
			uaSaved = this.userAccountService.save(uaSaved);
			result.setUserAccount(uaSaved);
			this.folderService.setFoldersByDefault(result);
		} else {
			final Author old = this.authorService.findOne(author.getId());

			ua.setPassword(hash);
			if (!old.getUserAccount().getUsername().equals(ua.getUsername()))
				Assert.isTrue(this.userAccountRepository.findByUsername(ua.getUsername()) == null, "The username is register");

			result = this.authorService.save(author);

		}

		return result;
	}

	public ActorForm inyect(final Actor actor) {
		final ActorForm result = new ActorForm();

		result.setAddress(actor.getAddress());
		result.setEmail(actor.getEmail());
		result.setId(actor.getId());
		result.setName(actor.getName());
		result.setPhone(actor.getPhone());
		result.setPhoto(actor.getPhoto());
		result.setSurname(actor.getSurname());
		result.setVersion(actor.getVersion());

		result.setUserAccountpassword(actor.getUserAccount().getPassword());
		result.setUserAccountuser(actor.getUserAccount().getUsername());
		result.setVersion(actor.getVersion());

		return result;
	}
}
