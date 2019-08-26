
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
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


	@Test
	public void driverCreateAndSubmits() {

		final Object testingData[][] = {
			{
				//				A: Educafy Crear y guardar una Reservation
				//				B: Test Positivo: Creación correcta de una Reservation
				//				C: % Recorre 196 de la 196 lineas posibles
				//				D: % cobertura de datos=8/32 (casos cubiertos / combinaciones posibles de atributos entre ellos)
				"author1", "conference1", "paper1", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSubmits((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void templateCreateAndSubmits(final String author, final String conference, final String paper, final Class<?> expected) {

		Class<?> caught;
		Submission submission;
		caught = null;
		try {
			this.authenticate(author);
			submission = this.submissionService.create(this.getEntityId(conference));
			submission.setAuthor(this.authorService.findOne(this.getEntityId(author)));
			submission.setConference(this.conferenceService.findOne(this.getEntityId(conference)));
			this.submissionService.submits(submission, this.paperService.findOne(this.getEntityId(paper)));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
