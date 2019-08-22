
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

	@Query("select t from Topic t where t.spanish LIKE CONCAT('%',?1,'%') and t.english LIKE CONCAT('%',?2,'%')")
	Collection<Topic> findTopicByNames(String spanishName, String englishName);
}
