
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import repositories.DashboardRepository;
import domain.Author;
import domain.Conference;
import domain.Paper;

@Service
@Transactional
public class DashboardService {

	@Autowired
	private DashboardRepository				dashboardRepository;

	@Autowired
	private AuthorRepository				authorRepository;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private ConferenceService				conferenceService;

	@Autowired
	private PaperService					paperService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;


	/**
	 * Statistics of the number of submissions per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberSubmissionPerConference() {
		final Double[] res = this.dashboardRepository.getNumberSubmissionPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of registrations per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberRegistrationPerConference() {
		final Double[] res = this.dashboardRepository.getNumberRegistrationPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the conference fees.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getConferenceFees() {
		final Double[] res = this.dashboardRepository.getConferenceFees();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of days per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberOfDaysPerConference() {
		final Double[] res = this.dashboardRepository.getNumberOfDaysPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the number of conferences per category.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberOfConferencesPerCategory() {
		final Double[] res = this.dashboardRepository.getNumberOfConferencesPerCategory();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the number of the number of comments per conference.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberCommentsPerConference() {
		final Double[] res = this.dashboardRepository.getNumberCommentsPerConference();
		Assert.notNull(res);
		return res;
	}

	/**
	 * Statistics of the number of the number of the number of the number of comments per activity.
	 * 
	 * @return Double[] Average, minimum, maximum and standard deviation (in order).
	 * @author a8081
	 * */
	public Double[] getNumberCommentsPerActivity() {
		final Double[] res = this.dashboardRepository.getNumberCommentsPerActivity();
		Assert.notNull(res);
		return res;
	}

	// AUTHOR SCORE

	public Double computeAuthorsScore() {
		this.administratorService.findByPrincipal();
		final Collection<Author> authors = this.authorService.findAll();
		final Collection<String> buzzWords = this.findAllBuzzWords();
		Double maximumScore = 0.;

		for (final Author author : authors) {
			Double score = 0.;
			for (final Paper cameraReadyPaper : this.paperService.findByAuthorUAId(author.getUserAccount().getId()))
				for (final String bw : buzzWords)
					if (cameraReadyPaper.getTitle().toLowerCase().matches("(.*)" + bw + "(.*)") || cameraReadyPaper.getSummary().toLowerCase().matches("(.*)" + bw + "(.*)"))
						score += 1;

			if (score > maximumScore)
				maximumScore = score;

			author.setScore(score);
		}

		// No tiene sentido realizar el calculo si el score maximo es 0. Sino le seteo un valor al score, este se quedara a 0
		if (maximumScore != 0)
			for (final Author author : authors) {
				author.setScore(author.getScore() / maximumScore);
				this.authorRepository.save(author);
			}
		return maximumScore;
	}
	public Collection<String> findAllBuzzWords() {
		final Collection<String> res = new ArrayList<>();

		final Collection<String> words = this.findBuzzWords();

		Map<String, Integer> buzzWFreq;
		buzzWFreq = new HashMap<>();

		for (final String w : words) {
			final Integer v = buzzWFreq.get(w);
			if (v != null)
				buzzWFreq.put(w.toLowerCase(), v + 1);
			else
				buzzWFreq.put(w.toLowerCase(), 1);
		}

		final Double max = this.max(buzzWFreq.values()) * 0.8;

		for (final String word : buzzWFreq.keySet())
			if (buzzWFreq.get(word) > max)
				res.add(word);

		return res;
	}
	private Collection<String> findBuzzWords() {
		final Collection<String> res = new ArrayList<>();

		for (final Conference conference : this.conferenceService.findLast12MonthOrFuture()) {
			String all = conference.getTitle() + " " + conference.getSummary();
			all = all.replaceAll("\\d", "");

			for (final String voidWord : this.configurationParametersService.find().getVoidWords())
				all = all.replaceAll("\\b" + voidWord + "\\b", "");

			res.addAll(Arrays.asList(all.split("\\W+")));
		}

		return res;
	}

	private Integer max(final Collection<Integer> input) {
		int max = 0;

		for (final Integer i : input)
			if (i > max)
				max = i;

		return max;
	}

}
