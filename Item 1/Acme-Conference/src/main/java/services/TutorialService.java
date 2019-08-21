
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService extends ActivityService {

	@Autowired
	private TutorialRepository		tutorialRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SectionService			sectionService;


	@Override
	public Tutorial create() {
		this.administratorService.findByPrincipal();
		return new Tutorial();
	}

	@Override
	public Tutorial findOne(final int tutorialId) {
		Assert.isTrue(tutorialId != 0);
		final Tutorial res = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Tutorial> findTutorials() {
		final Collection<Tutorial> res = this.tutorialRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Collection<Tutorial> findTutorialsByConference(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		final Collection<Tutorial> tutorials = this.tutorialRepository.findTutorialsByConference(conferenceId);
		Assert.notNull(tutorials);
		return tutorials;
	}

	public void delete(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		this.administratorService.findByPrincipal();
		Assert.isTrue(this.tutorialRepository.findOne(tutorial.getId()).equals(tutorial), "No se puede borrar un tutorial que no existe");
		this.tutorialRepository.delete(tutorial);
		this.sectionService.deleteAll(tutorial.getSections());
	}

}
