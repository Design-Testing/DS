
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelRepository;
import domain.Panel;

@Service
@Transactional
public class PanelService extends ActivityService {

	@Autowired
	private PanelRepository			panelRepository;

	@Autowired
	private AdministratorService	administratorService;


	@Override
	public Panel create() {
		this.administratorService.findByPrincipal();
		return new Panel();
	}

	@Override
	public Panel findOne(final int panelId) {
		Assert.isTrue(panelId != 0);
		final Panel res = this.panelRepository.findOne(panelId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Panel> findPanelsByConference(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		final Collection<Panel> panels = this.panelRepository.findPanelsByConference(conferenceId);
		Assert.notNull(panels);
		return panels;
	}

}
