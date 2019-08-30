
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r join r.submission s where r.submission.id=?1 and (s.author.id=?2 or r.reviewer.id=?2)")
	Collection<Report> findReportsBySubmission(int submissionId, int actorId);

	@Query("select r from Report r where r.submission.id=?1 and r.reviewer.id=?2")
	Report findReportBySubmissionAndReviewer(int submissionId, int reviewerId);

	@Query("select sum(1.0) from Report r where r.decision='ACCEPT' and r.isDraft=false and r.submission.id=?1")
	Double countFinalAcceptReportBySubmission(int submissionId);

	@Query("select sum(1.0) from Report r where r.decision='REJECT' and r.isDraft=false and r.submission.id=?1")
	Double countFinalRejectReportBySubmission(int submissionId);

	@Query("select sum(1.0) from Report r where r.decision='BORDER-LINE' and r.isDraft=false and r.submission.id=?1")
	Double countFinalBorderLineReportBySubmission(int submissionId);

	@Query("select case when count(r) > 0 then true else false end from Report r join r.submission s where r.id=?2 and (s.author.id=?1 or r.reviewer.id=?1)")
	boolean isMyReport(int actorId, int reportId);

	@Query("select r from Report r where r.reviewer.id=?1")
	Collection<Report> findReportsByReviewer(int reviewerId);

}
