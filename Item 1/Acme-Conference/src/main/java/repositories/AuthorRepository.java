
package repositories;

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
}
