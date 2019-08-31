
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuoletRepository;
import domain.Conference;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository		quoletRepository;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administratorService;


	//Métodos CRUD

	public Quolet create(final int auditId) {
		final Quolet res = new Quolet();
		res.setIsDraft(true);
		final Date moment = new Date(System.currentTimeMillis() - 100);
		res.setMoment(moment);
		final Conference conference = this.conferenceService.findOne(auditId);
		Assert.isTrue(conference.getIsDraft().equals(false));
		res.setConference(conference);
		this.administratorService.findByPrincipal();
		//Assert.isTrue(res.getConference().getAdministrator().equals(principal), "No puede guardar una quolet que no es tuya.");
		final String ticker = this.generateTicker(moment);
		res.setTicker(ticker);
		return res;
	}

	public Quolet findOne(final int quoletId) {
		Assert.isTrue(quoletId != 0);
		final Quolet res = this.quoletRepository.findOne(quoletId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Quolet> findAll() {
		final Collection<Quolet> res = this.quoletRepository.findAll();
		Assert.notNull(res, "find all quolets return null");
		return res;
	}

	/** To save after create and after update a quolet **/
	public Quolet save(final Quolet quolet, final int auditId) {
		Assert.notNull(quolet);
		this.administratorService.findByPrincipal();
		final Conference conference = this.conferenceService.findOne(auditId);
		final Quolet res;
		if (quolet.getId() != 0) {
			final Quolet retrieved = this.quoletRepository.findOne(quolet.getId());
			Assert.isTrue(retrieved.getIsDraft(), "quolet.draft.edit.error");
		} else {
			quolet.setConference(conference);
			quolet.setIsDraft(true);
		}
		res = this.quoletRepository.save(quolet);
		return res;

	}

	/** To publish a quolet: to final mode **/
	public Quolet publish(final Quolet quolet) {
		Assert.notNull(quolet);
		final Quolet res;
		this.administratorService.findByPrincipal();
		final Quolet retrieved = this.quoletRepository.findOne(quolet.getId());
		Assert.notNull(retrieved, "quolet.no.exists.error");
		Assert.isTrue(retrieved.getIsDraft(), "quolet.publish.error");
		quolet.setTicker(retrieved.getTicker());
		quolet.setIsDraft(false);
		res = this.quoletRepository.save(quolet);
		return res;
	}

	/** To delete a quolet **/
	public void delete(final Quolet quolet) {
		Assert.notNull(quolet);
		this.administratorService.findByPrincipal();
		final Quolet retrieved = this.quoletRepository.findOne(quolet.getId());
		Assert.notNull(retrieved, "quolet.no.exists.error");
		Assert.isTrue(retrieved.getIsDraft().equals(true), "quolet.draft.error");
		this.quoletRepository.delete(quolet);
	}

	public Collection<Quolet> findQuoletsByConference(final int auditId) {
		final Collection<Quolet> res = this.quoletRepository.findQuoletsByConference(auditId);
		Assert.notNull(res, "findQuoletsByConference returns null");
		return res;
	}

	/** El ticker es XXXX-yymmdd donde XXXX es un string aleatorio de longitud 4 y yymmdd es el moment del quolet **/
	private String generateTicker(final Date moment) {
		String res = "";
		final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String s1 = Character.toString(letters.charAt((int) Math.floor(Math.random() * 26)));
		final String s2 = Character.toString(letters.charAt((int) Math.floor(Math.random() * 26)));
		final int number1 = (int) Math.floor(Math.random() * 9);
		final int number2 = (int) Math.floor(Math.random() * 9);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(moment);
		Integer year = (int) cal.get(Calendar.YEAR);
		year = year % 100;
		String year2 = Integer.toString(year);
		if (year2.length() == 1)
			year2 = "0" + year2;
		final Integer month = cal.get(Calendar.MONTH) + 1;
		String month2 = Integer.toString(month);
		if (month2.length() == 1)
			month2 = "0" + month2;
		final Integer day = (int) cal.get(Calendar.DAY_OF_MONTH);
		String day2 = Integer.toString(day);
		if (day2.length() == 1)
			day2 = "0" + day2;

		final String ticker = year2 + month2 + day2 + number1 + number2 + s1 + s2;

		res = ticker;

		final Collection<Quolet> quolets = this.quoletRepository.getQuoletWithTicker(ticker);
		if (!quolets.isEmpty())
			this.generateTicker(moment);
		return res;
	}

}
