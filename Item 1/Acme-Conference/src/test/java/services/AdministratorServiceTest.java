
package services;

import java.util.ArrayList;
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
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void create() {
		super.authenticate("admin1");
		final Administrator admin = this.administratorService.create();
		Assert.notNull(admin);
	}

	@Test
	public void save() {
		super.authenticate("admin1");
		Administrator admin = this.administratorService.create();
		Assert.notNull(admin);

		final UserAccount ua = new UserAccount();
		final List<Authority> authorities = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		ua.setAuthorities(authorities);
		ua.setUsername("admin43");
		ua.setPassword(HashPassword.hashPassword("admin43"));

		admin.setUserAccount(ua);
		admin.setAddress("Avenida Reina");
		admin.setEmail("theboss@jmsx.es");
		admin.setName("Pepe");
		admin.setMiddleName("Bolino");
		admin.setSurname("Shur-name");

		admin = this.administratorService.save(admin);
		Assert.isTrue(admin.getId() != 0);
	}
}
