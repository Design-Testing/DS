
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ConfigurationParameters;

@Repository
public interface ConfigurationParametersRepository extends JpaRepository<ConfigurationParameters, Integer> {

	@Query("select c.welcomeMessageEsp from ConfigurationParameters c")
	String findWelcomeMessageEsp();

	@Query("select c.welcomeMessageEn from ConfigurationParameters c")
	String findWelcomeMessageEn();

	@Query("select c.banner from ConfigurationParameters c")
	String findBanner();

	@Query("select c.sysName from ConfigurationParameters c")
	String findSysName();

}
