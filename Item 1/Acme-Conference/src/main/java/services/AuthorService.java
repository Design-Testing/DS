
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

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.HashPassword;
import domain.Actor;
import domain.Author;
import domain.Finder;
import forms.ActorForm;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private AuthorRepository	authorRepository;

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


	public Author create() {
		final Author author = new Author();
		return author;
	}

	public Author findOne(final int authorId) {
		Assert.isTrue(authorId != 0);
		final Author result = this.authorRepository.findOne(authorId);
		Assert.notNull(result);
		return result;
	}

	public Boolean checkForEmailInUse(final String email) {
		Boolean res = false;
		final String inUse = this.authorRepository.checkForEmailInUse(email);
		if (inUse != null)
			res = true;
		return res;
	}

	public Author save(final Author author) {
		Assert.notNull(author);
		Author result;

		if (author.getId() == 0) {
			final String username = author.getUserAccount().getUsername();
			final String password = HashPassword.hashPassword(author.getUserAccount().getPassword());
			final Finder finder = this.finderService.createForNewActor();
			author.setFinder(finder);
			final Author withUserAccount = (Author) this.actorService.setUserAccount(Authority.AUTHOR, author, username, password);
			Assert.isTrue(this.checkForEmailInUse(withUserAccount.getEmail()) == false, "Email is already in use");
			result = this.authorRepository.save(withUserAccount);

			this.folderService.setFoldersByDefault(result);

		} else {
			final String password = author.getUserAccount().getPassword();
			final Actor principal = this.actorService.findByPrincipal();
			author.getUserAccount().setPassword(password);
			Assert.isTrue(principal.getId() == author.getId(), "You only can edit your info");
			result = this.authorRepository.save(author);
		}
		return result;
	}

	public void delete(final Author author) {
		Assert.notNull(author);
		Assert.isTrue(this.findByPrincipal().equals(author));
		Assert.isTrue(author.getId() != 0);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == author.getId(), "You only can edit your info");
		Assert.isTrue(this.authorRepository.exists(author.getId()));
		this.authorRepository.delete(author);
	}

	public Collection<Author> findAll() {
		final Collection<Author> res = this.authorRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	/* ========================= OTHER METHODS =========================== */

	public Author findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Author author = this.findByUserId(user.getId());
		Assert.notNull(author);
		final boolean bool = this.actorService.checkAuthority(author, Authority.AUTHOR);
		Assert.isTrue(bool);

		return author;
	}

	public Author findByUserId(final int id) {
		Assert.isTrue(id != 0);
		final Author author = this.authorRepository.findByUserId(id);
		return author;
	}

	public void flush() {
		this.authorRepository.flush();
	}

	public Author reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Author author;

		if (actorForm.getId() == 0) {
			author = this.create();
			author.setName(actorForm.getName());
			author.setSurname(actorForm.getSurname());
			author.setMiddleName(actorForm.getMiddleName());
			author.setPhoto(actorForm.getPhoto());
			author.setPhone(actorForm.getPhone());
			Assert.isTrue(this.checkForEmailInUse(actorForm.getEmail()) == false, "Email is already in use");
			author.setEmail(actorForm.getEmail());
			author.setAddress(actorForm.getAddress());
			author.setVersion(actorForm.getVersion());
			author.setFinder(this.finderService.create());
			//			author.setScore(0.0);
			//			author.setSpammer(false);
			final UserAccount account = this.userAccountService.create();
			final Collection<Authority> authorities = new ArrayList<>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.AUTHOR);
			authorities.add(auth);
			account.setAuthorities(authorities);
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			author.setUserAccount(account);
		} else {
			author = this.authorRepository.findOne(actorForm.getId());
			author.setName(actorForm.getName());
			author.setSurname(actorForm.getSurname());
			author.setPhoto(actorForm.getPhoto());
			author.setPhone(actorForm.getPhone());
			if (!author.getEmail().equals(actorForm.getEmail()))
				Assert.isTrue(this.checkForEmailInUse(actorForm.getEmail()) == false, "Email is already in use");
			author.setEmail(actorForm.getEmail());
			author.setAddress(actorForm.getAddress());
			author.setVersion(actorForm.getVersion());
			author.setFinder(this.finderService.findActorFinder());
			final UserAccount account = this.userAccountService.findOne(author.getUserAccount().getId());
			account.setUsername(actorForm.getUserAccountuser());
			account.setPassword(actorForm.getUserAccountpassword());
			author.setUserAccount(account);
		}

		this.validator.validate(author, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return author;
	}

}
