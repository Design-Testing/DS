
package controllers.administrator;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private AdministratorService	administratorService;

	final String					lang	= LocaleContextHolder.getLocale().getLanguage();


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView viewCategory(@RequestParam final int categoryId) {
		this.administratorService.findByPrincipal();

		final ModelAndView result;
		final Category category = this.categoryService.findOne(categoryId);
		result = new ModelAndView("category/display");
		result.addObject("category", category);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "category/display.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView viewCategories() {
		this.administratorService.findByPrincipal();

		final ModelAndView result;
		final Collection<Category> categories = this.categoryService.findAll();
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("isAdministrator", true);
		result.addObject("requestURI", "category/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editCategory(@RequestParam final int categoryId) {
		final ModelAndView result;
		this.administratorService.findByPrincipal();

		final Category category = this.categoryService.findOne(categoryId);
		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCategory(@ModelAttribute("category") @Valid final Category category, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("category/edit");
			result.addObject("errors", binding.getAllErrors());
			result.addObject("category", category);

		} else
			try {
				this.categoryService.save(category);
				result = this.viewCategory(category.getId());
			} catch (final ValidationException oops) {
				result = new ModelAndView("category/edit");
				result.addObject("category", category);
				result.addObject("errors", "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteCategory(@RequestParam final int categoryId) {
		this.categoryService.delete(categoryId);
		final ModelAndView result = new ModelAndView("category/delete");
		result.addObject("requestURI", "category/list.do");
		return result;
	}
}
