
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	// =======================================
	// ========== Dashboard queries ==========
	// =======================================

	/** The average, the minimum, the maximum, and the standard deviation of the number of sponsorships per sponsor. */
	@Query("select avg(1.0+(select count(sp) from Sponsorship sp where sp.sponsor.id=pro.id)-1.0), min(1.0+(select count(sp) from Sponsorship sp where sp.sponsor.id=pro.id)-1.0), max(1.0+(select count(sp) from Sponsorship sp where sp.sponsor.id=pro.id)-1.0),stddev(1.0+(select count(sp) from Sponsorship sp where sp.sponsor.id=pro.id)-1.0) from Sponsor pro")
	Double[] getStatisticsOfSponsorshipPerSponsor();
	// =======================================
	// =======================================

	@Query("select sp from Sponsorship sp where sp.sponsor.userAccount.id=?1")
	Collection<Sponsorship> findAllByUserId(final Integer userAccountId);

	//@Query("select s from Sponsorship s where s.position.id=?1")
	//Collection<Sponsorship> findByPosition(int positionId);

	//TODO ERROR DE VALIDACIÓN AL CORRER SERVIDOR
	//@Query("select s from Sponsorship s where s.position.id=?1 and s.sponsor.userAccount.id=?2")
	//Sponsorship findByPosition(int positionId, int sponsorUAId);

	//TODO ERROR DE VALIDACIÓN AL CORRER SERVIDOR
	//@Query("select case when (count(sp) > 0) then false else true end from Sponsorship sp where sp.position.id=?1 and sp.sponsor.userAccount.id=?2")
	//boolean availableSponsorshipPosition(int positionId, int sponsorUAId);

	@Query("select sp from Sponsorship sp where sp.creditCard.expirationYear<?2 or (sp.creditCard.expirationMonth<?1 and sp.creditCard.expirationYear=?2)")
	Collection<Sponsorship> expiredSponsorships(int month, int year);

}
