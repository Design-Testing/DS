
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	@Autowired
	private PresentationRepository	presentationRepository;


	//METODOS CRUD
	public Presentation create() {

		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - create");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - create");

		final Presentation res = new Presentation();
		res.setTitle("");
		res.setStartMoment(null);
		res.setHours(0);
		res.setMinutes(0);
		res.setRoom("");
		res.setSummary("");
		res.setCameraReadyPaper(null);
		final Collection<String> attachment = new ArrayList<>();
		res.setAttachments(attachment);

		final Collection<Actor> speakers = new ArrayList<>();
		res.setSpeakers(speakers);

		return res;
	}

	public Presentation findOne(final int idPresentation) {
		Assert.isTrue(idPresentation != 0, "El idPresentation debe ser distinto de 0");
		final Presentation res = this.presentationRepository.findOne(idPresentation);
		Assert.notNull(res);
		return res;
	}

	public Collection<Presentation> findAll() {
		final Collection<Presentation> res = this.presentationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Presentation save(final Presentation presentation) {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - save");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - save");

		Assert.notEmpty(presentation.getSpeakers());
		Assert.notNull(presentation.getCameraReadyPaper(), "The Camera Redy can't be null");
		final Presentation res = this.presentationRepository.save(presentation);
		return res;
	}
}
