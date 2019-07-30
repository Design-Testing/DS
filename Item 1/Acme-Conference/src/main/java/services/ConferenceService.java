
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Conference;
import forms.ConferenceForm;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository	conferenceRepository;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private Validator				validator;


	//METODOS CRUD
	//TODO JMSX
	public Conference create() {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - create");

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - create");

		final Collection<Category> categories = this.categoryService.findCategoriesWithNameConference();
		final Category category = categories.iterator().next();

		System.out.println("CATEGORIES" + categories);
		System.out.println("CATEGORY" + category);

		final Conference res = new Conference();
		res.setTitle("");
		res.setAcronym("");
		res.setVenue("");
		res.setSubmission(null);
		res.setNotification(null);
		res.setCameraReady(null);
		res.setStartDate(null);
		res.setEndDate(null);
		res.setCategory(category);
		res.setSummary("");
		res.setFee(0.0);
		res.setIsDraft(true);

		return res;
	}

	public Conference findOne(final int idConference) {
		Assert.isTrue(idConference != 0, "El idConference debe ser distinto de 0");
		final Conference res = this.conferenceRepository.findOne(idConference);
		Assert.notNull(res);
		return res;
	}

	public Collection<Conference> findAll() {
		final Collection<Conference> res = this.conferenceRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Conference save(final Conference conference) {
		final UserAccount usuario = LoginService.getPrincipal();
		Assert.notNull(usuario, "Debes estar logueado - save");

		if (conference.getId() != 0) {
			final Conference retrieved = this.findOne(conference.getId());
			Assert.isTrue(retrieved.getIsDraft().equals(true), "The conference must be in draft mode");
		}

		conference.setIsDraft(true);

		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);
		Assert.isTrue(usuario.getAuthorities().contains(admin), "Debes ser admin - save");

		Assert.notNull(conference.getCategory(), "The category can't be null");
		final Conference res = this.conferenceRepository.save(conference);
		return res;
	}

	//TODO ALBA ** hacer que no se pueda pasar a final hasta que tenga alguna actividad?
	public void toFinalMode(final Conference conference) {
		final Conference retrieved = this.findOne(conference.getId());
		Assert.notNull(retrieved, "the conference does not exist");
		Assert.isTrue(retrieved.getIsDraft().equals(true), "The conference must be in draft mode");
		conference.setIsDraft(false);
		this.conferenceRepository.save(conference);
	}

	//OTRO METODOS
	public Collection<Conference> findConferences(final String keyword, final String categoryName, final Date fromDate, final Date toDate, final Double maximumFee) {
		final Collection<Conference> res = this.conferenceRepository.findConferences(keyword, categoryName, fromDate, toDate, maximumFee);
		Assert.notNull(res);
		return res;
	}

	public Collection<Conference> findFurthcomingConferences() {
		final Collection<Conference> result = this.conferenceRepository.findFurthcomingConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findPastConferences() {
		final Collection<Conference> result = this.conferenceRepository.findPastConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findRunningConferences() {
		final Collection<Conference> result = this.conferenceRepository.findRunningConferences();
		Assert.notNull(result);
		return result;
	}

	public Collection<Conference> findFiveDaysFromSubmissionConferences() {
		final Date now = new Date();
		final Collection<Conference> result = new ArrayList<Conference>();
		for (final Conference c : this.findAll())
			if (c.getSubmission().before(now))
				if ((now.getTime() - c.getSubmission().getTime()) / (1000 * 60 * 60 * 24) <= 5)
					result.add(c);
		return result;
	}

	public Collection<Conference> findFiveDaysForNotificationConferences() {
		final Date now = new Date();
		final Collection<Conference> result = new ArrayList<Conference>();
		for (final Conference c : this.findAll())
			if (now.before(c.getNotification()))
				if ((c.getNotification().getTime() - now.getTime()) / (1000 * 60 * 60 * 24) < 5)
					result.add(c);
		return result;
	}

	public Collection<Conference> findFiveDaysForCameraReadyConferences() {
		final Date now = new Date();
		final Collection<Conference> result = new ArrayList<Conference>();
		for (final Conference c : this.findAll())
			if (now.before(c.getCameraReady()))
				if ((c.getCameraReady().getTime() - now.getTime()) / (1000 * 60 * 60 * 24) < 5)
					result.add(c);
		return result;
	}
	public Collection<Conference> findFiveDaysForBeginningConferences() {
		final Date now = new Date();
		final Collection<Conference> result = new ArrayList<Conference>();
		for (final Conference c : this.findAll())
			if (now.before(c.getStartDate()))
				if ((c.getStartDate().getTime() - now.getTime()) / (1000 * 60 * 60 * 24) < 5)
					result.add(c);
		return result;
	}

	public ConferenceForm constructPruned(final Conference conference) {
		final ConferenceForm pruned = new ConferenceForm();

		pruned.setId(conference.getId());
		pruned.setVersion(conference.getVersion());
		pruned.setTitle(conference.getTitle());
		pruned.setAcronym(conference.getAcronym());
		pruned.setVenue(conference.getVenue());
		pruned.setSubmission(conference.getSubmission());
		pruned.setNotification(conference.getNotification());
		pruned.setCameraReady(conference.getCameraReady());
		pruned.setStartDate(conference.getStartDate());
		pruned.setEndDate(conference.getEndDate());
		pruned.setSummary(conference.getSummary());
		pruned.setFee(conference.getFee());
		pruned.setCategory(conference.getCategory());

		return pruned;
	}

	public Conference reconstruct(final ConferenceForm conferenceForm, final BindingResult binding) {
		Conference result;

		if (conferenceForm.getId() == 0)
			result = this.create();
		else
			result = this.findOne(conferenceForm.getId());

		result.setVersion(conferenceForm.getVersion());
		result.setTitle(conferenceForm.getTitle());
		result.setAcronym(conferenceForm.getAcronym());
		result.setVenue(conferenceForm.getVenue());
		result.setSubmission(conferenceForm.getSubmission());
		result.setNotification(conferenceForm.getNotification());
		result.setCameraReady(conferenceForm.getCameraReady());
		result.setStartDate(conferenceForm.getStartDate());
		result.setEndDate(conferenceForm.getEndDate());
		result.setSummary(conferenceForm.getSummary());
		result.setFee(conferenceForm.getFee());
		result.setCategory(conferenceForm.getCategory());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
