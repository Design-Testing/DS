
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import domain.Actor;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository	reportRepository;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private ActorService		actorService;


	public Report create(final int submissionId, final int reviewerId) {
		final Report res = new Report();
		res.setOriginality(0);
		res.setReadability(0);
		res.setQuality(0);
		res.setDecision("BORDER-LINE");
		res.setIsDraft(true);
		final Submission submission = this.submissionService.findOne(submissionId);
		Assert.notNull(submission);
		//TODO under-reviewed
		//Assert.isTrue(submission.getStatus().equals("UNDER-REVIEWED"), "no se puede crear un report sobre una submission que no esté en estado PRE-REVIEWED");
		final Reviewer reviewer = this.reviewerService.findOne(reviewerId);
		Assert.notNull(reviewer);

		return res;
	}

	public Report save(final Report report, final Submission submission, final Reviewer reviewer) {
		Assert.notNull(report);
		Assert.notNull(submission);
		Assert.notNull(reviewer);
		Assert.isTrue(report.getDecision().matches("^(BORDER-LINE|ACCEPT|REJECT)$"));
		Assert.notNull(report.getReadability());
		Assert.isTrue(report.getReadability() >= 0 && report.getReadability() <= 10);
		Assert.notNull(report.getQuality());
		Assert.isTrue(report.getQuality() >= 0 && report.getQuality() <= 10);
		Assert.notNull(report.getOriginality());
		Assert.isTrue(report.getOriginality() >= 0 && report.getOriginality() <= 10);

		report.setSubmission(submission);
		report.setReviewer(reviewer);

		final Report res = this.reportRepository.save(report);
		Assert.notNull(res);

		return res;
	}

	public Report findOne(final Integer reportId) {
		Assert.notNull(reportId);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.reportRepository.isMyReport(principal.getId(), reportId));
		final Report res = this.reportRepository.findOne(reportId);

		if (this.actorService.checkAuthority(principal, Authority.AUTHOR))
			Assert.isTrue(res.getSubmission().getIsNotified());

		Assert.notNull(res);

		return res;
	}
	public Collection<Report> findAll() {
		final Collection<Report> res = this.reportRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public void delete(final Integer reportId) {
		Assert.notNull(reportId);
		this.reportRepository.delete(reportId);
	}

	public Collection<Report> findReportsBySubmission(final int submissionId) {
		final Actor principal = this.actorService.findByPrincipal();
		if (this.actorService.checkAuthority(principal, Authority.AUTHOR))
			Assert.isTrue(this.submissionService.findOne(submissionId).getIsNotified());
		final Collection<Report> result = this.reportRepository.findReportsBySubmission(submissionId, principal.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Report> findReportsByReviewer(final int reviewerId) {
		final Collection<Report> result = this.reportRepository.findReportsByReviewer(reviewerId);
		Assert.notNull(result);
		return result;
	}

	public Report findReportBySubmissionAndReviewer(final int submissionId, final int reviewerId) {
		final Report result = this.reportRepository.findReportBySubmissionAndReviewer(submissionId, reviewerId);
		return result;
	}
}
