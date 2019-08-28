
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Reviewer;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {

	@Query("select h from Reviewer h where h.userAccount.id=?1")
	Reviewer findByUserId(Integer studentId);

	@Query("select r from Reviewer r join r.keywords k where exists (select c from Conference c where c.id=?1 AND (c.title LIKE CONCAT('%',k,'%') OR c.summary LIKE CONCAT('%',k,'%')))")
	Collection<Reviewer> findReviewersAccordingToConference(int conferenceId);

	@Query("select r.reviewer from Report r where r.submission.id=?1")
	Collection<Reviewer> findReviewersAssignedToSubmission(int submissionId);

}
