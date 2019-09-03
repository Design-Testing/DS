
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select distinct p from Conference p join p.category c where p.isDraft=false and (?1='' OR p.acronym LIKE CONCAT('%',?1,'%') OR p.title LIKE CONCAT('%',?1,'%') OR p.venue LIKE CONCAT('%',?1,'%') OR p.summary LIKE CONCAT('%',?1,'%')) AND (?2='' OR c.titleEn LIKE CONCAT('%',?2,'%') OR c.titleEs LIKE CONCAT('%',?2,'%')) AND ((p.fee<=?5) OR ?5=NULL) AND ((p.endDate>=?3) OR ?3=NULL) AND ((p.startDate<=?4) OR ?4=NULL)")
	Collection<Conference> findConferences(String keyword, String categoryName, Date fromDate, Date toDate, Double maximumFee);

	@Query("select c from Conference c where c.startDate > CURRENT_TIMESTAMP")
	Collection<Conference> findFurthcomingConferences();

	@Query("select c from Conference c where c.endDate < CURRENT_TIMESTAMP")
	Collection<Conference> findPastConferences();

	@Query("select c from Conference c where c.endDate > CURRENT_TIMESTAMP and c.startDate < CURRENT_TIMESTAMP")
	Collection<Conference> findRunningConferences();

	@Query("select distinct p from Conference p join p.category c where p.isDraft=false and (?1='' OR p.acronym LIKE CONCAT('%',?1,'%') OR p.title LIKE CONCAT('%',?1,'%') OR p.venue LIKE CONCAT('%',?1,'%') OR p.summary LIKE CONCAT('%',?1,'%'))")
	Collection<Conference> findConferences(String keyword);

	@Query("select c from Conference c join c.activities ac where ac.id=?1")
	Conference findConference(int activityId);

	//@Query("select c from Conference c WHERE ((month(CURRENT_TIMESTAMP) - month(c.submission) = 0 AND day(CURRENT_TIMESTAMP) - day(c.submission) <=5 AND day(CURRENT_TIMESTAMP) - day(c.submission) >=0) OR ((month(CURRENT_TIMESTAMP) - month(c.submission) = 1 OR month(CURRENT_TIMESTAMP) - month(c.submission) = -11 ) AND day(CURRENT_TIMESTAMP) - day(c.submission) <=-25 AND day(CURRENT_TIMESTAMP) - day(c.submission) >=-30)) ")
	@Query("select c from Conference c where DATEDIFF(CURRENT_TIMESTAMP,c.submission ) <= 5 and DATEDIFF(CURRENT_TIMESTAMP,c.submission ) >= 0")
	Collection<Conference> findFiveDaysFromSubmissionConferences();

	//@Query("select c from Conference c WHERE ((month(c.notification) - month(CURRENT_TIMESTAMP) = 0 AND day(c.notification) - day(CURRENT_TIMESTAMP) <=5 AND day(c.notification) - day(CURRENT_TIMESTAMP) >=0) OR ((month(c.notification) - month(CURRENT_TIMESTAMP) = 1 OR month(c.notification) - month(CURRENT_TIMESTAMP) = -11 )AND day(c.notification) - day(CURRENT_TIMESTAMP) <=-25 AND day(c.notification) - day(CURRENT_TIMESTAMP) >=-30)) ")
	@Query("select c from Conference c where DATEDIFF(c.notification , CURRENT_TIMESTAMP) <= 5 and DATEDIFF(c.notification , CURRENT_TIMESTAMP) >= 0")
	Collection<Conference> findFiveDaysForNotificationConferences();

	//@Query("select c from Conference c WHERE ((month(c.cameraReady) - month(CURRENT_TIMESTAMP) = 0 AND day(c.cameraReady) - day(CURRENT_TIMESTAMP) <=5 AND day(c.cameraReady) - day(CURRENT_TIMESTAMP) >=0) OR ((month(c.cameraReady) - month(CURRENT_TIMESTAMP) = 1 OR month(c.cameraReady) - month(CURRENT_TIMESTAMP) = -11) AND day(c.cameraReady) - day(CURRENT_TIMESTAMP) <=-25 AND day(c.cameraReady) - day(CURRENT_TIMESTAMP) >=-30)) ")
	@Query("select c from Conference c where DATEDIFF(c.cameraReady , CURRENT_TIMESTAMP) <= 5 and DATEDIFF(c.cameraReady , CURRENT_TIMESTAMP) >= 0 ")
	Collection<Conference> findFiveDaysForCameraReadyConferences();

	//@Query("select c from Conference c WHERE ((month(c.startDate) - month(CURRENT_TIMESTAMP) = 0 AND day(c.startDate) - day(CURRENT_TIMESTAMP) <=5 AND day(c.startDate) - day(CURRENT_TIMESTAMP) >=0) OR ((month(c.startDate) - month(CURRENT_TIMESTAMP) = 1 OR month(c.startDate) - month(CURRENT_TIMESTAMP) = -11) AND day(c.startDate) - day(CURRENT_TIMESTAMP) <=-25 AND day(c.startDate) - day(CURRENT_TIMESTAMP) >=-30)) ")
	@Query("select c from Conference c where DATEDIFF(c.startDate , CURRENT_TIMESTAMP)<= 5 and  DATEDIFF(c.startDate , CURRENT_TIMESTAMP) >= 0")
	Collection<Conference> findFiveDaysForBeginningConferences();

	@Query("select ac from Conference c join c.activities ac WHERE c.id=?1")
	Collection<Activity> findConferenceActivities(int conferenceId);

	@Query("select c from Conference c where c.isDraft=false")
	Collection<Conference> findAllFinal();

	@Query("select c from Conference c where c.startDate > CURRENT_TIMESTAMP and  c.isDraft=false")
	Collection<Conference> findFinalFurthcomingConferences();

	@Query("select c from Conference c where c.endDate < CURRENT_TIMESTAMP and  c.isDraft=false")
	Collection<Conference> findFinalPastConferences();

	@Query("select c from Conference c where c.endDate > CURRENT_TIMESTAMP and c.startDate < CURRENT_TIMESTAMP  and  c.isDraft=false")
	Collection<Conference> findFinalRunningConferences();

	@Query("select r.conference from Registration r where r.author.userAccount.id=?1")
	Collection<Conference> findAllByAuthorUserId(int authorUAId);

	//@Query("select c from Registration r join r.conference c where c.startDate > CURRENT_TIMESTAMP and  c.isDraft=false and r.author.userAccount.id<>?1")
	//Collection<Conference> findAvailableByAuthorUserId(int authorUAId);

	@Query("select c from Conference c where c.startDate > CURRENT_TIMESTAMP and c.isDraft=false and 0=1.0*(select count(r) from Registration r where r.author.id=?1 and r.conference.id=c.id)")
	Collection<Conference> findConferenceAvailableToRegistration(int authorId);

	@Query("select r.conference from Registration r where r.author.id=?1")
	Collection<Conference> findConferenceWithRegistration(int authorId);

	@Query("select c from Conference c where c.category.id=?1")
	Collection<Conference> findByCategory(int categoryId);

	@Query("select c from Conference c join c.activities a where a.id=?1 ")
	Conference findConferenceByTutorialId(int tutorialId);

	@Query("select c from Conference c where DATEDIFF(CURRENT_DATE, c.startDate)<=365")
	Collection<Conference> findLast12MonthOrFuture();

	@Query("select distinct p from Conference p join p.category c where (?1='' OR p.acronym LIKE CONCAT('%',?1,'%') OR p.title LIKE CONCAT('%',?1,'%') OR p.venue LIKE CONCAT('%',?1,'%') OR p.summary LIKE CONCAT('%',?1,'%'))")
	Collection<Conference> findAllConferences(String keyword);

	@Query("select distinct p from Conference p join p.category c where (?1='' OR p.acronym LIKE CONCAT('%',?1,'%') OR p.title LIKE CONCAT('%',?1,'%') OR p.venue LIKE CONCAT('%',?1,'%') OR p.summary LIKE CONCAT('%',?1,'%')) AND (?2='' OR c.titleEn LIKE CONCAT('%',?2,'%') OR c.titleEs LIKE CONCAT('%',?2,'%')) AND ((p.fee<=?5) OR ?5=NULL) AND ((p.endDate>=?3) OR ?3=NULL) AND ((p.startDate<=?4) OR ?4=NULL)")
	Collection<Conference> findAllConferences(String keyword, String categoryName, Date fromDate, Date toDate, Double maximumFee);

	@Query("select distinct p from Conference p join p.category c where p.isDraft=false and (?1='' OR c.titleEn LIKE CONCAT('%',?1,'%') OR c.titleEs LIKE CONCAT('%',?1,'%'))")
	Collection<Conference> findConferencesGroupedByCategory(String categoryName);

}
