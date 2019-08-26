
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	@Query("select r from Registration r where r.conference.id=?1")
	Collection<Registration> findByConference(int conferenceId);

	@Query("select r from Registration r where r.author.userAccount.id=?1")
	Collection<Registration> findAllByAuthorUserId(int id);

	@Query("select r from Registration r where r.author.id=?1 AND r.conference.id=?2")
	Registration findByConferenceAndPrincipal(int id, int conferenceId);

}
