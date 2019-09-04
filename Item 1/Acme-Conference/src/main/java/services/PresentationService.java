
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Activity;
import domain.Conference;
import domain.Presentation;

@Service
@Transactional
public class PresentationService extends ActivityService {

	@Autowired
	private PresentationRepository	presentationRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private PaperService			paperService;


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

	public Presentation save(final Presentation presentation, final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		this.administratorService.findByPrincipal();
		Assert.isTrue(this.paperService.findByConference(conferenceId).contains(presentation.getCameraReadyPaper()));
		Assert.notNull(presentation, "Activity is null - save");
		final Conference conference = this.conferenceService.findOne(conferenceId);
		Assert.isTrue(conference.getIsDraft(), "La conferencia asociada a la actividad que estar en modo draft");
		Assert.notNull(conference);
		Assert.notEmpty(presentation.getSpeakers());
		final Collection<Activity> activities = this.conferenceService.findConferenceActivities(conferenceId);
		if (presentation.getId() != 0)
			Assert.isTrue(activities.contains(presentation));
		final Presentation res = this.presentationRepository.save(presentation);
		if (presentation.getId() == 0) {
			activities.add(res);
			conference.setActivities(activities);
		}
		this.conferenceService.save(conference);
		return res;
	}
}
