
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("SELECT s FROM Submission s WHERE s.author=?1")
	List<Submission> getSubmissionsByAuthor(Integer authorId);

	@Query("SELECT s FROM Submission s WHERE s.author=?1 GROUP BY status")
	List<Submission> getSubmissionsByAuthorGroupByStatus(Integer authorId);

}
