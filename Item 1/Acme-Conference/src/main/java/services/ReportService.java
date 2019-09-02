
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
	private ReportRepository		reportRepository;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	public Report create(final int submissionId, final int reviewerId) {
		final Report res = new Report();
		res.setOriginality(0);
		res.setReadability(0);
		res.setQuality(0);
		res.setDecision("BORDER-LINE");
		res.setIsDraft(true);
		final Submission submission = this.submissionService.findOne(submissionId);
		Assert.notNull(submission);
		final Reviewer reviewer = this.reviewerService.findOne(reviewerId);
		Assert.notNull(reviewer);

		return res;
	}

	public Report save(final Report report, final Submission submission, final Reviewer reviewer) {
		Assert.notNull(report);
		Assert.notNull(submission);
		Assert.notNull(reviewer);

		if (report.getId() == 0) {
			this.administratorService.findByPrincipal();
			Assert.isTrue(report.getIsDraft() == true, "a report created by default must be in draft mode");
		} else { //cuando un revisor lo edita
			final Reviewer principal = this.reviewerService.findByPrincipal();
			Assert.isTrue(principal.getUserAccount().getId() == reviewer.getUserAccount().getId());
		}

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
		final Reviewer principal = this.reviewerService.findByPrincipal();
		Assert.isTrue(this.reportRepository.findOne(reportId).getReviewer().getId() == principal.getId());
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

	public Collection<Report> findAllReportsBySubmission(final int submissionId) {
		final Collection<Report> result = this.reportRepository.findAllReportsBySubmission(submissionId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Report> findReportsByReviewer(final int reviewerId) {
		final Collection<Report> result = this.reportRepository.findReportsByReviewer(reviewerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Report> findDraftReportsByReviewer(final int reviewerId) {
		final Collection<Report> result = this.reportRepository.findDraftReportsByReviewer(reviewerId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Report> findFinalReportsByReviewer(final int reviewerId) {
		final Collection<Report> result = this.reportRepository.findFinalReportsByReviewer(reviewerId);
		Assert.notNull(result);
		return result;
	}

	public Report findReportBySubmissionAndReviewer(final int submissionId, final int reviewerId) {
		final Report result = this.reportRepository.findReportBySubmissionAndReviewer(submissionId, reviewerId);
		return result;
	}

	public Double countFinalAcceptReportBySubmission(final int submissionId) {
		Double result = this.reportRepository.countFinalAcceptReportBySubmission(submissionId);
		if (result == null)
			result = 0.0;
		return result;
	}

	public Double countFinalRejectReportBySubmission(final int submissionId) {
		Double result = this.reportRepository.countFinalRejectReportBySubmission(submissionId);
		if (result == null)
			result = 0.0;
		return result;
	}

	public Double countFinalBorderLineReportBySubmission(final int submissionId) {
		Double result = this.reportRepository.countFinalBorderLineReportBySubmission(submissionId);
		if (result == null)
			result = 0.0;
		return result;
	}
}
