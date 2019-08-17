
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

	public Activity save(final Activity activity) {
		this.administratorService.findByPrincipal();

		Assert.notEmpty(activity.getSpeakers());

		final Activity res = this.activityRepository.save(activity);
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
}
