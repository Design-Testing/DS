
package services;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConferenceServiceTest extends AbstractTest {

	// Services

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private CategoryService		categoryService;


	/* ========================= Test Create and Save =========================== */

	@Test
	public void driverCreateAndSave() {

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un administrador puede crear una conferencia
				//				B: Test Positivo: un administrador crea una conferencia correctamente
				"admin1", "ttile1", "acronym1", "venue1", 0.1, "summary1", "category1", "2019-12-01 00:00", "2019-12-02 00:00", "2019-12-03 00:00", "2019-12-04 00:00", "2019-12-05 00:00", null
			}, {
				//				A: Acme-Conference: un administrador puede crear una conferencia
				//				B: Test Negativo: un administrador intenta crear una conferencia con el plazo de notificación anterior al de presentación
				"admin2", "ttile2", "acronym2", "venue2", 0.2, "summary2", "category2", "2019-12-02 00:00", "2019-12-01 00:00", "2019-12-03 00:00", "2019-12-04 00:00", "2019-12-05 00:00", java.lang.IllegalArgumentException.class
			}, {
				//				A: Acme-Conference: un administrador puede crear una conferencia
				//				B: Test Negativo: un author intenta crear una conferencia
				"author1", "ttile3", "acronym3", "venue3", 0.3, "summary3", "category3", "2019-12-01 00:00", "2019-12-02 00:00", "2019-12-03 00:00", "2019-12-04 00:00", "2019-12-05 00:00", java.lang.IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
	}
	private void templateCreateAndSave(final String admin, final String title, final String acronym, final String venue, final Double fee, final String summary, final String category, final String submission, final String notification,
		final String cameraReady, final String startDate, final String endDate, final Class<?> expected) {

		Class<?> caught;
		Conference conference;
		caught = null;

		try {
			this.authenticate(admin);
			conference = this.conferenceService.create();
			conference.setTitle(title);
			conference.setAcronym(acronym);
			conference.setSummary(summary);
			conference.setVenue(venue);
			conference.setCategory(this.categoryService.findOne(this.getEntityId(category)));
			conference.setSubmission((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(submission));
			conference.setNotification((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(notification));
			conference.setCameraReady((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(cameraReady));
			conference.setStartDate((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(startDate));
			conference.setEndDate((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(endDate));
			this.conferenceService.save(conference);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/* ========================= Test Edit =========================== */

	@Test
	public void driverEdit() {

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un administrador puede ediatr una conferencia en modo borrador
				//				B: Test Positivo: un administrador edita una conferencia correctamente
				"admin1", "conference5", "title1", "acronym1", "venue1", 0.1, "summary1", "category1", "2019-12-01 00:00", "2019-12-02 00:00", "2019-12-03 00:00", "2019-12-04 00:00", "2019-12-05 00:00", null
			}, {
				//				A: Acme-Conference: un administrador puede ediatr una conferencia en modo borrador
				//				B: Test Positivo: un administrador intenta editar una conferencia en modo final
				"admin1", "conference1", "title1", "acronym1", "venue1", 0.1, "summary1", "category1", "2019-12-01 00:00", "2019-12-02 00:00", "2019-12-03 00:00", "2019-12-04 00:00", "2019-12-05 00:00", java.lang.IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (String) testingData[i][12], (Class<?>) testingData[i][13]);
	}

	private void templateEdit(final String admin, final String conference, final String title, final String acronym, final String venue, final Double fee, final String summary, final String category, final String submission, final String notification,
		final String cameraReady, final String startDate, final String endDate, final Class<?> expected) {

		Class<?> caught;
		final Conference confRetrieved;
		caught = null;

		try {
			this.authenticate(admin);
			confRetrieved = this.conferenceService.findOne(this.getEntityId(conference));
			confRetrieved.setTitle(title);
			confRetrieved.setAcronym(acronym);
			confRetrieved.setSummary(summary);
			confRetrieved.setVenue(venue);
			confRetrieved.setCategory(this.categoryService.findOne(this.getEntityId(category)));
			confRetrieved.setSubmission((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(submission));
			confRetrieved.setNotification((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(notification));
			confRetrieved.setCameraReady((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(cameraReady));
			confRetrieved.setStartDate((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(startDate));
			confRetrieved.setEndDate((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(endDate));
			this.conferenceService.save(confRetrieved);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
