
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService	categoryService;


	@Test
	public void createTest() {
		super.authenticate("admin1");
		final Category category = this.categoryService.create();
		Assert.notNull(category);
		super.unauthenticate();

	}

	@Test
	public void saveTest() {
		super.authenticate("admin1");
		final List<Category> categories = new ArrayList<Category>(this.categoryService.findAll());
		final Category category = categories.get(1);
		category.setTitleEs("TEST");
		final Category saved = this.categoryService.save(category);
		Assert.isTrue(category.getTitleEs().equals(saved.getTitleEs()));
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveRootCategoryTest() {
		super.authenticate("admin1");
		final List<Category> categories = new ArrayList<Category>(this.categoryService.findAll());
		final Category category = categories.get(0);
		category.setTitleEs("TEST");
		this.categoryService.save(category);
		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteRootCategoryTest() {
		super.authenticate("admin1");
		final List<Category> categories = new ArrayList<Category>(this.categoryService.findAll());
		final Category category = categories.get(0);
		this.categoryService.delete(category.getId());
		super.unauthenticate();
	}

	@Test
	public void deleteTest() {
		super.authenticate("admin1");
		final List<Category> categories = new ArrayList<Category>(this.categoryService.findAll());
		final Category category = categories.get(1);
		this.categoryService.delete(category.getId());
		super.unauthenticate();
	}
}
