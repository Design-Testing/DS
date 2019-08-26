
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
}
