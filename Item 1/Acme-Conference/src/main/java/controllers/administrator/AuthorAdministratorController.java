
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.DashboardService;
import controllers.AbstractController;
import domain.Author;

@Controller
@RequestMapping("/administrator/author")
public class AuthorAdministratorController extends AbstractController {

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private DashboardService	dashboardService;


	// COMPUTE SCORE ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAuthors() {
		final ModelAndView res;
		final Collection<Author> authors = this.authorService.findAll();

		res = new ModelAndView("author/list");
		res.addObject("authors", authors);
		return res;
	}

	@RequestMapping(value = "/computeScore", method = RequestMethod.GET)
	public ModelAndView computeScore() {
		final ModelAndView res;
		final Collection<Author> authors = this.authorService.findAll();

		final Double maxScore = this.dashboardService.computeAuthorsScore();

		res = new ModelAndView("author/list");
		res.addObject("authors", authors);
		res.addObject("maxScore", maxScore);
		if (maxScore > 0.)
			res.addObject("msg", "score.process.ok");
		else
			res.addObject("msg", "score.process.no");

		return res;
	}
}
