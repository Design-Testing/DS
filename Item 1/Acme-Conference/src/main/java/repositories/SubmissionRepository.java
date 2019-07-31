
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("SELECT s FROM Submission s WHERE s.author.id=?1")
	List<Submission> getSubmissionsByAuthor(int authorId);

	//@Query("SELECT s FROM Submission s WHERE s.author=?1 GROUP BY status")
	//List<Submission> getSubmissionsByAuthorGroupByStatus(Integer authorId);

	@Query("select s from Submission s where s.ticker=?1")
	Collection<Submission> getSubmissionWithTicker(String res);

	@Query("select s from Submission s where s.conference.id=?1")
	Collection<Submission> findSubmissionsByConference(int conferenceId);

	@Query("select s from Submission s where s.conference.id=?1 and s.status='ACCEPTED'")
	Collection<Submission> findAcceptedSubmissionsByConference(int conferenceId);

	@Query("select s from Submission s where s.conference.id=?1 and s.status='REJECTED'")
	Collection<Submission> findRejectedSubmissionsByConference(int conferenceId);

	@Query("select s from Submission s where s.conference.id=?1 and s.status='UNDER-REVIEWED'")
	Collection<Submission> findUnderReviewedSubmissionsByConference(int conferenceId);

}
