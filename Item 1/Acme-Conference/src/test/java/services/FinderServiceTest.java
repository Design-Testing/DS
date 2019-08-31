
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	FinderService		finderService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	ActivityService		activityService;

	@Autowired
	ReportService		reportService;

	@Autowired
	LoginService		loginService;


	@Test
	public void createTest() {
		final Finder finder = this.finderService.create();
	}

	@Test
	public void saveTest() {
		super.authenticate("author1");
		final Finder finder = this.finderService.findOne(3184);
		this.finderService.save(finder);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveUnauthenticatedTest() {
		this.unauthenticate();
		final Finder finder = this.finderService.findOne(3184);
		this.finderService.save(finder);
	}

	@Test
	public void deleteTest() {
		super.authenticate("author1");
		final Finder finder = this.finderService.findOne(3184);
		this.finderService.delete(finder);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUnauthenticatedTest() {
		final Finder finder = this.finderService.findOne(3184);
		this.finderService.delete(finder);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNotOwnedTest() {
		super.authenticate("author1");
		final Finder finder = this.finderService.findOne(3185);
		this.finderService.delete(finder);
		super.unauthenticate();
	}

	@Test
	public void clearTest() {
		super.authenticate("author1");
		final Finder finder = this.finderService.findOne(3184);
		this.finderService.clear(finder);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void clearUnauthenticatedTest() {
		final Finder finder = this.finderService.findOne(3184);
		this.finderService.clear(finder);
	}

	@Test(expected = IllegalArgumentException.class)
	public void clearNotOwnedTest() {
		super.authenticate("author1");
		final Finder finder = this.finderService.findOne(3185);
		this.finderService.clear(finder);
		super.unauthenticate();
	}
}
