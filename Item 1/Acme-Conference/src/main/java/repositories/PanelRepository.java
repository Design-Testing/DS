
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Panel;

@Repository
public interface PanelRepository extends JpaRepository<Panel, Integer> {

	@Query("select t from Panel t where (select c.id from Conference c join c.activities ac where ac.id=t.id)=?1")
	Collection<Panel> findPanelsByConference(int conferenceId);

}
