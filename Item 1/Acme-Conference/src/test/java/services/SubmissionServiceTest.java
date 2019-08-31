
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Paper;
import domain.Submission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private PaperService		paperService;


	/* ========================= Test Create and Submits =========================== */

	@Test
	public void driverCreateAndSubmits() {

		final Collection<String> authors = new ArrayList<String>();
		authors.add("author1");
		final Paper paper = this.paperService.create();
		paper.setDocument("http://www.document.com");
		paper.setSummary("summary");
		paper.setTitle("title");
		paper.setAuthors(authors);

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un autor puede solicitar su presentación en una conferencia
				//				B: Test Positivo: autor solicita correctamente una presentación
				"author1", "conference7", paper, null
			}, {
				//				A: Acme-Conference: un autor puede solicitar su presentación en una conferencia
				//				B: Test Positivo: autor intenta solicitar presentación a una conferencia cuyo plazo de presentación ha expirado
				"author1", "conference6", paper, java.lang.IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSubmits((String) testingData[i][0], (String) testingData[i][1], (Paper) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateCreateAndSubmits(final String author, final String conference, final Paper paper, final Class<?> expected) {

		Class<?> caught;
		Submission submission;
		caught = null;
		try {
			this.authenticate(author);
			submission = this.submissionService.create(this.getEntityId(conference));
			submission.setAuthor(this.authorService.findOne(this.getEntityId(author)));
			submission.setConference(this.conferenceService.findOne(this.getEntityId(conference)));
			this.submissionService.submits(submission, paper);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/* ========================= Test Send Camera-Ready Paper =========================== */

	@Test
	public void driverSendCameraReadyPaper() {

		final Collection<String> authors = new ArrayList<String>();
		authors.add("author1");
		final Paper paper = this.paperService.create();
		paper.setDocument("http://www.document.com");
		paper.setSummary("summary");
		paper.setTitle("title");
		paper.setAuthors(authors);

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un autor puede enviar un documento definitivo para alguna de sus solicitudes de presentación
				//				B: Test Positivo: un autor envia un documento definitivo adecuadamente
				"author1", "submission4", paper, null
			}, {
				//				A: Acme-Conference: un autor puede enviar un documento definitivo para alguna de sus solicitudes de presentación
				//				B: Test Negativo: un autor intenta enviar un documento definitivo sobre una conferencia cuyo plazo de presentación de tal documento ha expirado
				"author1", "submission3", paper, java.lang.IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSendCameraReadyPaper((String) testingData[i][0], (String) testingData[i][1], (Paper) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateSendCameraReadyPaper(final String author, final String submission, final Paper paper, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {
			this.authenticate(author);
			this.submissionService.sendCameraReadyPaper(this.getEntityId(submission), paper);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/* ========================= Test Assign to Reviewer =========================== */

	@Test
	public void driverAssignReviewer() {

		final Collection<String> authors = new ArrayList<String>();
		authors.add("author1");
		final Paper paper = this.paperService.create();
		paper.setDocument("http://www.document.com");
		paper.setSummary("summary");
		paper.setTitle("title");
		paper.setAuthors(authors);

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un administrador asigna una solicitud de presentación a un revisor
				//				B: Test Positivo: un administrador asigna una solicitud de presentación a un revisor correctamente
				"reviewer2", "submission8", java.lang.IllegalArgumentException.class
			}, {
				//				A: Acme-Conference: un administrador asigna una solicitud de presentación a un revisor
				//				B: Test Positivo: un administrador intenta asignar a un revisor una solicitud de presentación de una confrencia cuyo plazo de notificación ha expirado
				"reviewer2", "submission1", java.lang.IllegalArgumentException.class
			}, {
				//				A: Acme-Conference: un administrador asigna una presentación a un revisor
				//				B: Test Negativo: un administrador intenta asignar a un revisor una solicitud de presentación que ya tiene asignada
				"reviewer1", "submission1", java.lang.IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAssignReviewer((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void templateAssignReviewer(final String reviewer, final String submission, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {
			this.authenticate("admin1");
			this.submissionService.assignToReviewer(this.getEntityId(submission), this.getEntityId(reviewer));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
