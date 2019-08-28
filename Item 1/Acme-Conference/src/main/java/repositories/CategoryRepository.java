
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.titleEn = 'CONFERENCE'")
	Collection<Category> findCategoriesWithNameConference();

	@Query("select c.titleEn from Category c")
	Collection<String> findCategoriesNameEn();

	@Query("select c.titleEs from Category c")
	Collection<String> findCategoriesNameEs();

	@Query("select c from Category c where c.father.id = ?1 and c.father.titleEn <> 'CONFERENCE'")
	Collection<Category> findCategoriesWichFatherIs(int categoryId);
}
