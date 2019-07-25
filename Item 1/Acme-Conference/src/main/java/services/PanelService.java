
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	@Autowired
	private PanelRepository	panelRepository;


	//METODOS CRUD
	public Panel create() {

		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - create");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - create");

		final Panel res = new Panel();
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

		return res;
	}

	public Panel findOne(final int idPanel) {
		Assert.isTrue(idPanel != 0, "El idPanel debe ser distinto de 0");
		final Panel res = this.panelRepository.findOne(idPanel);
		Assert.notNull(res);
		return res;
	}

	public Collection<Panel> findAll() {
		final Collection<Panel> res = this.panelRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Panel save(final Panel panel) {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - save");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - save");

		Assert.notEmpty(panel.getSpeakers());

		final Panel res = this.panelRepository.save(panel);
		return res;
	}
}
