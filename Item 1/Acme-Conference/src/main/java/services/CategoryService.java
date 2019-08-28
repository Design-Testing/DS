
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Autowired
	private ConferenceService	conferenceService;


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
		Assert.isTrue(!(category.getTitleEn().equals("CONFERENCE")), "You can´t edit root category 'Conference'.");

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
		Assert.isTrue(!(this.categoryRepository.findOne(categoryId).getTitleEn().equals("CONFERENCE")), "You can´t delete root category 'Conference'.");

		final List<Category> categoriasDependientes = new ArrayList<Category>(this.categoryRepository.findCategoriesWichFatherIs(categoryId));

		if (categoriasDependientes.isEmpty()) {
			this.conferenceService.reassignConferences(categoryId);
			this.categoryRepository.delete(categoryId);
			System.out.println("Se ha eliminado la categoria" + categoryId);
		} else {
			for (int i = 0; i < categoriasDependientes.size(); i++) {
				System.out.println(categoriasDependientes.get(i));
				this.delete(categoriasDependientes.get(i).getId());
			}
			this.conferenceService.reassignConferences(categoryId);
			this.categoryRepository.delete(categoryId);
			System.out.println("Se ha eliminado la categoria" + categoryId);
		}

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

	public Collection<Category> findSubcategCategories(final int categoryId) {
		return this.categoryRepository.findSubCategories(categoryId);
	}
}
