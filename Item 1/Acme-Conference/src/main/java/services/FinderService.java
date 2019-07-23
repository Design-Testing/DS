
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository				finderRepository;

	@Autowired
	private ConfigurationParametersService	configParamService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private ConferenceService				conferenceService;


	/**
	 * The average, minimum, maximum and standard deviation of the number of conferences per company
	 * 
	 * @author a8081
	 */
	public Double[] getStatisticsOfConferencesPerFinder() {
		final Double[] res = this.finderRepository.getStatisticsOfConferencesPerFinder();
		Assert.notNull(res);
		return res;
	}

	/**
	 * The ratio of empty versus non empty finders
	 * 
	 * @author a8081
	 * */
	public Double findRatioFinders() {
		return this.finderRepository.findRatioFinders();
	}

	//Metodos CRUD

	public Finder create() {
		final Finder finder = new Finder();
		finder.setKeyword("");
		finder.setCategoryName("");
		finder.setFromDate(null);
		finder.setMaximumFee(null);
		finder.setToDate(null);
		final Collection<Conference> ps = new ArrayList<Conference>();
		//finder.setCreationDate(new Date(System.currentTimeMillis()));
		finder.setConferences(ps);
		return finder;
	}

	public Collection<Finder> findAll() {
		final Collection<Finder> res = this.finderRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Finder findOne(final int finderId) {
		Assert.notNull(finderId);
		Assert.isTrue(finderId != 0);
		final Finder res = this.finderRepository.findOne(finderId);
		Assert.notNull(res);
		return res;
	}

	// Antes de guardar tengo que pasar por este metodo para setearle las nuevas procesiones segun los nuevos parametros
	public Finder find(final Finder finder) {
		this.actorService.findByPrincipal();
		final List<Conference> result = new ArrayList<>(this.conferenceService.findConferences(finder.getKeyword(), finder.getCategoryName(), finder.getFromDate(), finder.getToDate(), finder.getMaximumFee()));
		//final int maxResults = this.configParamService.find().getMaxFinderResults();
		//if (result.size() > maxResults) {
		//		Collections.shuffle(result);
		//		result = result.subList(0, maxResults);
		//}
		finder.setConferences(result);
		return this.save(finder);
	}

	public Finder save(final Finder finder) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.findActorFinder(actor.getId()).getId() == finder.getId(), "You're not owner of this finder, you cannot modify it");

		//finder.setCreationDate(new Date(System.currentTimeMillis()));
		final Finder res = this.finderRepository.save(finder);
		Assert.notNull(res);

		actor.setFinder(finder);
		this.actorService.save(actor);
		return res;
	}

	public Finder createForNewActor() {
		final Finder finder = new Finder();
		finder.setKeyword("");
		finder.setCategoryName("");
		finder.setFromDate(null);
		finder.setMaximumFee(null);
		finder.setToDate(null);
		final Collection<Conference> ps = new ArrayList<Conference>();
		//finder.setCreationDate(new Date(System.currentTimeMillis()));
		finder.setConferences(ps);
		final Finder res = this.finderRepository.save(finder);
		return res;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));
		Assert.isTrue(this.finderRepository.findActorFinder(actor.getId()).getId() == finder.getId(), "You're not owner of this finder, you cannot delete it");
		this.finderRepository.delete(finder);
	}

	public Finder findActorFinder() {
		final Actor principal = this.actorService.findByPrincipal();

		final Finder finder = this.finderRepository.findActorFinder(principal.getId());
		Assert.notNull(finder);

		// final int finderTime = this.configParamService.find().getFinderTime();
		// final LocalDateTime ldt = new LocalDateTime(finder.getCreationDate());
		// ldt.plusHours(finderTime);

		// if (ldt.isBefore(LocalDateTime.now()))
		// if (this.clearCache(finder))
		//	this.clear(finder);

		return finder;
	}

	public Finder clear(final Finder finder) {
		final Actor actor = this.actorService.findByPrincipal();
		final Finder result = this.finderRepository.findActorFinder(actor.getId());
		Assert.isTrue(result.equals(finder), "You're not owner of this finder");
		Assert.notNull(result);
		finder.setKeyword("");
		finder.setCategoryName("");
		finder.setFromDate(null);
		finder.setMaximumFee(null);
		finder.setToDate(null);
		//finder.setCreationDate(new Date(System.currentTimeMillis()));
		result.setConferences(new ArrayList<Conference>());
		final Finder saved = this.save(result);
		return saved;
	}

	//	public boolean clearCache(final Finder finder) {
	//		Assert.notNull(finder);
	//		final double update = finder.getCreationDate().getTime();
	//		final double current = new Date(System.currentTimeMillis()).getTime();
	//		final Double period = (current - update) / 3600000;
	//		final int max = this.configParamService.find().getFinderTime();
	//		return max <= period;
	//	}

	public void flush() {
		this.finderRepository.flush();

	}

}
