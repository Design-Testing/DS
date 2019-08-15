
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DomainEntity;

@Repository
public interface DashboardRepository extends JpaRepository<DomainEntity, Integer> {

	/** Average, minimum, maximum and standard deviation of the number of submissions per conference. */
	@Query("select avg(1.0+ (select count(p) from Submission p where p.conference.id=c.id) -1.0), min(1.0+ (select count(p) from Submission p where p.conference.id=c.id) -1.0), max(1.0+ (select count(p) from Submission p where p.conference.id=c.id) -1.0), stddev(1.0+ (select count(p) from Submission p where p.conference.id=c.id) -1.0) from Conference c")
	Double[] getNumberSubmissionPerConference();

	/** Average, minimum, maximum and standard deviation of the number of registrations per conference. */
	@Query("select avg(1.0+ (select count(p) from Registration p where p.conference.id=c.id) -1.0), min(1.0+ (select count(p) from Registration p where p.conference.id=c.id) -1.0), max(1.0+ (select count(p) from Registration p where p.conference.id=c.id) -1.0), stddev(1.0+ (select count(p) from Registration p where p.conference.id=c.id) -1.0) from Conference c")
	Double[] getNumberRegistrationPerConference();

	/** Average, minimum, maximum and standard deviation of the conference fees. */
	@Query("select avg(c.fee), min(c.fee), max(c.fee), stddev(c.fee) from Conference c")
	Double[] getConferenceFees();

	/** Average, minimum, maximum and standard deviation of the number of days per conference. */
	@Query("select avg(day(c.endDate) - day(c.startDate)), min(day(c.endDate) - day(c.startDate)), max(day(c.endDate) - day(c.startDate)), stddev(day(c.endDate) - day(c.startDate)) from Conference c")
	Double[] getNumberOfDaysPerConference();

	/** Average, minimum, maximum and standard deviation of the number of conferences per category. */
	@Query("select avg(1.0+ (select count(p) from Conference p where p.category.id=c.id) -1.0), min(1.0+ (select count(p) from Conference p where p.category.id=c.id) -1.0), max(1.0+ (select count(p) from Conference p where p.category.id=c.id) -1.0), stddev(1.0+ (select count(p) from Conference p where p.category.id=c.id) -1.0) from Category c")
	Double[] getNumberOfConferencesPerCategory();

	/** Average, minimum, maximum and standard deviation of the number of comments per conference. */
	@Query("select avg(1.0+ (select count(p) from Comment p where p.conference.id=c.id) -1.0), min(1.0+ (select count(p) from Comment p where p.conference.id=c.id) -1.0), max(1.0+ (select count(p) from Comment p where p.conference.id=c.id) -1.0), stddev(1.0+ (select count(p) from Comment p where p.conference.id=c.id) -1.0) from Conference c")
	Double[] getNumberCommentsPerConference();

	/** Average, minimum, maximum and standard deviation of the number of comments per activity. */
	@Query("select avg(1.0+ (select count(p) from Comment p where p.activity.id=c.id) -1.0), min(1.0+ (select count(p) from Comment p where p.activity.id=c.id) -1.0), max(1.0+ (select count(p) from Comment p where p.activity.id=c.id) -1.0), stddev(1.0+ (select count(p) from Comment p where p.activity.id=c.id) -1.0) from Activity c")
	Double[] getNumberCommentsPerActivity();

}
