
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// Sustituir "ClassName" por el nombre de la nueva clase sobre la que se quiere redactar los comentarios
	//
	// @Query("select c from Comment c where c.className.id=?1")
	// Collection<Comment> findByClassName(int classNameId);

	@Query("select c from Comment c where c.panel.id=?1")
	Collection<Comment> findByPanel(int panelId);

	@Query("select c from Comment c where c.tutorial.id=?1")
	Collection<Comment> findByTutorial(int tutorialId);

	@Query("select c from Comment c where c.presentation.id=?1")
	Collection<Comment> findByPresentation(int presentationId);

	@Query("select c from Comment c where c.conference.id=?1")
	Collection<Comment> findByConference(int conferenceId);

}
