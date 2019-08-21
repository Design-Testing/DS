
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Presentation;

@Service
@Transactional
public class PresentationService extends ActivityService {

	@Autowired
	private PresentationRepository	presentationRepository;

	@Autowired
	private AdministratorService	administratorService;


	@Override
	public Presentation create() {
		this.administratorService.findByPrincipal();
		return new Presentation();
	}

	@Override
	public Presentation findOne(final int presentationId) {
		Assert.isTrue(presentationId != 0);
		final Presentation res = this.presentationRepository.findOne(presentationId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Presentation> findPresentationsByConference(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		final Collection<Presentation> presentations = this.presentationRepository.findPresentationsByConference(conferenceId);
		Assert.notNull(presentations);
		return presentations;
	}

}
