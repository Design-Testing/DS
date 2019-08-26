
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import utilities.HashPassword;
import domain.Reviewer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReviewerServiceTest extends AbstractTest {

	@Autowired
	private ReviewerService	revieweristratorService;


	@Test
	public void create() {
		super.authenticate("reviewer1");
		final Reviewer reviewer = this.revieweristratorService.create();
		Assert.notNull(reviewer);
	}

	@Test
	public void save() {
		super.authenticate("reviewer1");
		Reviewer reviewer = this.revieweristratorService.create();
		Assert.notNull(reviewer);

		final UserAccount ua = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REVIEWER);
		authorities.add(authority);
		ua.setAuthorities(authorities);
		ua.setUsername("reviewer43");
		ua.setPassword(HashPassword.hashPassword("reviewer43"));

		reviewer.setUserAccount(ua);
		reviewer.setAddress("Avenida Reina");
		reviewer.setEmail("theboss@jmsx.es");
		reviewer.setName("Pepe");
		reviewer.setMiddleName("Bolino");
		reviewer.setSurname(Arrays.asList("Shur-name"));

		reviewer = this.revieweristratorService.save(reviewer);
		Assert.isTrue(reviewer.getId() != 0);
	}
}
