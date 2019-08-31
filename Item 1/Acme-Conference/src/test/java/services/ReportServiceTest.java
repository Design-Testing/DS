
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	ReportService		reportService;

	@Autowired
	LoginService		loginService;

	@Autowired
	SubmissionService	submissionService;

	@Autowired
	ReviewerService		reviewerService;


	@Test
	public void createTest() {
		super.authenticate("reviewer1");
		final Submission submision = this.submissionService.findOne(3236);
		final Reviewer reviewer = this.reviewerService.findOne(3195);
		final Report report = this.reportService.create(submision.getId(), reviewer.getId());
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void createUnauthenticatedTest() {
		final Submission submision = this.submissionService.findOne(3236);
		final Reviewer reviewer = this.reviewerService.findOne(3195);
		final Report report = this.reportService.create(submision.getId(), reviewer.getId());
	}

	@Test
	public void saveTest() {
		super.authenticate("reviewer1");
		final Submission submision = this.submissionService.findOne(3236);
		final Reviewer reviewer = this.reviewerService.findOne(3195);
		final Report report = this.reportService.findOne(3243);
		this.reportService.save(report, submision, reviewer);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveUnauthenticatedTest() {
		super.unauthenticate();
		final Submission submision = this.submissionService.findOne(3236);
		final Reviewer reviewer = this.reviewerService.findOne(3195);
		final Report report = this.reportService.findOne(3243);
		this.reportService.save(report, submision, reviewer);
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveNotOwnedTest() {
		super.authenticate("reviewer1");
		final Submission submision = this.submissionService.findOne(3236);
		final Reviewer reviewer = this.reviewerService.findOne(3195);
		final Report report = this.reportService.findOne(3244);
		this.reportService.save(report, submision, reviewer);
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		super.authenticate("reviewer1");
		final Report report = this.reportService.findOne(3243);
		this.reportService.delete(report.getId());
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUnauthenticatedTest() {
		super.unauthenticate();
		final Report report = this.reportService.findOne(3243);
		this.reportService.delete(report.getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteNotOwnedTest() {
		super.authenticate("reviewer1");
		final Report report = this.reportService.findOne(3244);
		this.reportService.delete(report.getId());
		super.unauthenticate();
	}

}
