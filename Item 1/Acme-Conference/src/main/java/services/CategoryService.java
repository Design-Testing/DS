
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

	public Category create() {
		final Category res = new Category();
		return res;
	}

	public Category save(final Category category) {
		Category res = new Category();
		Assert.notNull(category);
		Assert.isTrue(category.getTitleEn().trim() != "" && category.getTitleEs().trim() != "");

		res = this.categoryRepository.save(category);
		Assert.notNull(res);

		return res;
	}
	public Category findOne(final Integer categoryId) {
		Category res;
		Assert.notNull(categoryId);
		res = this.categoryRepository.findOne(categoryId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Category> findAll() {
		Collection<Category> res;
		res = this.categoryRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public void delete(final Integer categoryId) {
		Assert.notNull(categoryId);
		this.categoryRepository.delete(categoryId);
	}

	public Collection<String> findCategoriesName(final String lang) {
		Assert.notNull(lang);
		Collection<String> res;
		if (lang == "en")
			res = this.categoryRepository.findCategoriesNameEn();
		else
			res = this.categoryRepository.findCategoriesNameEs();
		Assert.notNull(res);
		return res;
	}
}
