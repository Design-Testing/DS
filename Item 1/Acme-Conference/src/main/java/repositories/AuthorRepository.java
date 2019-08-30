
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select h from Author h where h.userAccount.id=?1")
	Author findByUserId(Integer studentId);

	@Query("select a.email from Author a where a.email =?1")
	String checkForEmailInUse(String email);

	@Query("select DISTINCT(s.author) from Submission s where s.conference.id=?1")
	Collection<Author> findAuthorsToSubmissionToConference(int conferenceId);

	@Query("select DISTINCT(r.author) from Registration r where r.conference.id=?1")
	Collection<Author> findAuthorsToRegistrationToConference(int conferenceId);
}
