
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.submission.id=?1")
	Collection<Report> findReportsBySubmission(int submissionId);

	@Query("select r from Report r where r.submission.id=?1 and r.reviewer.id=?2")
	Report findReportBySubmissionAndReviewer(int submissionId, int reviewerId);

	@Query("select r from Report r where r.reviewer.id=?1")
	Collection<Report> findReportsByReviewer(int reviewerId);

}
