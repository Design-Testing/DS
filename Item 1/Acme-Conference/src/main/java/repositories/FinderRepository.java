
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	/** The average, minimum, maximum and standard deviation of the number of conferences per company */
	@Query("select avg(fi.conferences.size), min(fi.conferences.size), max(fi.conferences.size), stddev(fi.conferences.size) from Finder fi")
	Double[] getStatisticsOfConferencesPerFinder();

	/** The ratio of empty versus non empty finders */
	@Query("select sum(case when f.conferences.size=0 then 1.0 else 0.0 end) / sum(case when f.conferences.size>0 then 1.0 else 0.0 end) from Finder f")
	Double findRatioFinders();

	@Query("select m.finder from Actor m where m.id=?1")
	Finder findActorFinder(int id);
}
