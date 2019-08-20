
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Actor;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Tutorial;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository		activityRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConferenceService		conferenceService;


	//METODOS CRUD

	public Activity create() {
		this.administratorService.findByPrincipal();

		final Activity res = new Activity();
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
	public Activity findOne(final int idActivity) {
		this.administratorService.findByPrincipal();

		Assert.isTrue(idActivity != 0, "El idActivity debe ser distinto de 0");
		final Activity res = this.activityRepository.findOne(idActivity);
		Assert.notNull(res);
		return res;
	}
	public Collection<Activity> findAll() {
		this.administratorService.findByPrincipal();

		final Collection<Activity> res = this.activityRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Activity save(final Activity activity, final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		this.administratorService.findByPrincipal();
		Assert.notNull(activity, "Activity is null - save");
		final Conference conference = this.conferenceService.findOne(conferenceId);
		//		TODO: Decidir si la actividad se anyade solo en draft mode o siempre
		//	Assert.isTrue(conference.getIsDraft(), "La conferencia asociada a la actividad que estar en modo draft");
		Assert.notNull(conference);
		Assert.notEmpty(activity.getSpeakers());
		final Activity res = this.activityRepository.save(activity);
		final Collection<Activity> activities = conference.getActivities();
		activities.add(res);
		conference.setActivities(activities);
		this.conferenceService.save(conference);
		return res;
	}

	public String identifyActivity(final Activity activity) {
		String res = "error";
		if (activity instanceof Panel)
			res = "panel";
		else if (activity instanceof Presentation)
			res = "presentation";
		else if (activity instanceof Tutorial)
			res = "tutorial";

		return res;
	}

	public Collection<Activity> findByConference(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		final Collection<Activity> tutorials = this.activityRepository.findByConference(conferenceId);
		Assert.notNull(tutorials);
		return tutorials;
	}

}
