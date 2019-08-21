
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Presentation;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

	@Query("select t from Presentation t where (select c.id from Conference c join c.activities ac where ac.id=t.id)=?1")
	Collection<Presentation> findPresentationsByConference(int conferenceId);

}
