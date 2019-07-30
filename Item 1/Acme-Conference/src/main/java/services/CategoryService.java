
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;


	public Collection<Category> findCategoriesWithNameConference() {
		final Collection<Category> result = this.categoryRepository.findCategoriesWithNameConference();
		Assert.notNull(result);
		return result;
	}
}
