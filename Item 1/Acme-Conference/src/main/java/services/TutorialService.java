
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository	tutorialRepository;
	@Autowired
	private UserAccountService	userAccountService;


	//METODOS CRUD
	public Tutorial create() {

		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - create");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - create");

		final Tutorial res = new Tutorial();
		res.setTitle("");
		res.setStartMoment(null);
		res.setHours(0);
		res.setMinutes(0);
		res.setRoom("");
		res.setSummary("");

		final Collection<String> attachment = new ArrayList<>();
		res.setAttachments(attachment);

		final Collection<Actor> speakers = new ArrayList<>();
		res.setSpeakers(speakers);

		final Collection<Section> sections = new ArrayList<>();
		res.setSections(sections);

		return res;
	}

	public Tutorial findOne(final int idTutorial) {
		Assert.isTrue(idTutorial != 0, "El idTutorial debe ser distinto de 0");
		final Tutorial res = this.tutorialRepository.findOne(idTutorial);
		Assert.notNull(res);
		return res;
	}

	public Collection<Tutorial> findAll() {
		final Collection<Tutorial> res = this.tutorialRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Tutorial save(final Tutorial tutorial) {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - save");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - create");

		Assert.notEmpty(tutorial.getSpeakers());

		final Tutorial res = this.tutorialRepository.save(tutorial);
		return res;
	}
}
