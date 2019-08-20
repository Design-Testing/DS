
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Presentation;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

	@Query("select a from Conference c join c.activities a where c.id=?1")
	Collection<Presentation> findPresentationsByConference(int conferenceId);

}
