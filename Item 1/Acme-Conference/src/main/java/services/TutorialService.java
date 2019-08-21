
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Activity;
import domain.Conference;
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

	@Autowired
	private ConferenceService		conferenceService;


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

	public void delete(final Tutorial tutorial, final int conferenceId) {
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		this.administratorService.findByPrincipal();
		final Tutorial retrieved = this.tutorialRepository.findOne(tutorial.getId());
		Assert.isTrue(retrieved.equals(tutorial), "No se puede borrar un tutorial que no existe");
		final Conference conference = this.conferenceService.findOne(conferenceId);
		Collection<Activity> acs = conference.getActivities();
		acs = this.conferenceService.findConferenceActivities(conferenceId);
		Assert.isTrue(acs.contains(tutorial), "La conferencia sobre la que se va a eliminar el tutorial debe de contener a este entre sus actividades");
		acs.remove(tutorial);
		conference.setActivities(acs);
		this.conferenceService.save(conference);
		this.tutorialRepository.delete(retrieved);
		this.sectionService.deleteAll(retrieved.getSections());
	}

}
