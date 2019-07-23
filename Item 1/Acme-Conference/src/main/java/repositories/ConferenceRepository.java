
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select distinct p from Conference p join p.category c where (?1='' OR p.acronym LIKE CONCAT('%',?1,'%') OR p.title LIKE CONCAT('%',?1,'%') OR p.venue LIKE CONCAT('%',?1,'%') OR p.summary LIKE CONCAT('%',?1,'%')) AND (?2='' OR c.titleEn LIKE CONCAT('%',?2,'%') OR c.titleEs LIKE CONCAT('%',?2,'%')) AND ((p.fee<=?5) OR ?5=NULL) AND ((p.endDate>=?3) OR ?3=NULL) AND ((p.startDate<=?4) OR ?4=NULL)")
	Collection<Conference> findConferences(String keyword, String categoryName, Date fromDate, Date toDate, Double maximumFee);
}
