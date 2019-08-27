
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PaperServiceTest extends AbstractTest {

	@Autowired
	private PaperService	paperService;


	/* ========================= Test Create and Submits =========================== */

	@Test
	public void driverCreateAndSave() {

		final Collection<String> authors = new ArrayList<String>();
		authors.add("author1");

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un autor puede crear un papel
				//				B: Test Positivo: un autor crea un papel correctamente
				"author1", "http://www.document.com", "summary1", "title1", authors, null
			}, {
				//				A: Acme-Conference: un autor puede crear un papel
				//				B: Test Negativo; un autor intenta crear un papel con el título en blanco
				"author1", "http://www.document.com", "summary1", "", authors, java.lang.IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Collection<String>) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	private void templateCreateAndSave(final String author, final String document, final String summary, final String title, final Collection<String> authors, final Class<?> expected) {

		Class<?> caught;
		Paper paper;
		caught = null;
		try {
			this.authenticate(author);
			paper = this.paperService.create();
			paper.setDocument(document);
			paper.setTitle(title);
			paper.setSummary(summary);
			paper.setAuthors(authors);
			this.paperService.save(paper);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/* ========================= Test Edit =========================== */

	@Test
	public void driverEdit() {

		final Collection<String> authors = new ArrayList<String>();
		authors.add("author1");

		final Object testingData[][] = {
			{
				//				A: Acme-Conference: un autor puede editar un papel
				//				B: Test Positivo: un autor edita un papel correctamente
				"author1", "paper1", "http://www.document.com", "summary1", "title1", authors, null
			}, {
				//				A: Acme-Conference: un autor puede editar un papel
				//				B: Test Negativo; un autor intenta editar un papel con el resumen en blanco
				"author1", "paper1", "http://www.document.com", "summary1", "", authors, java.lang.IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Collection<String>) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	private void templateEdit(final String author, final String paper, final String document, final String summary, final String title, final Collection<String> authors, final Class<?> expected) {

		Class<?> caught;
		Paper paperRetrieved;
		caught = null;
		try {
			this.authenticate(author);
			paperRetrieved = this.paperService.findOne(this.getEntityId(paper));
			paperRetrieved.setDocument(document);
			paperRetrieved.setTitle(title);
			paperRetrieved.setSummary(summary);
			paperRetrieved.setAuthors(authors);
			this.paperService.save(paperRetrieved);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
