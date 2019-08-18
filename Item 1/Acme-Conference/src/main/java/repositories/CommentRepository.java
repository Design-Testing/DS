
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.conference.id=?1")
	Collection<Comment> findByConference(int conferenceId);

	@Query("select c from Comment c where c.activity.id=?1")
	Collection<Comment> findByActivity(int id);

	@Query("select c from Comment c where c.report.id=?1")
	Collection<Comment> findByReport(int id);

	// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo
	// @Query("select c from Comment c where c.quolet.id=?1")
	// Collection<Comment> findByQuolet(int id);
}
