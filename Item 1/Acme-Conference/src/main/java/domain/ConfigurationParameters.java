
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String				sysName;
	private String				banner;
	private String				welcomeMessageEsp;
	private String				welcomeMessageEn;
	private String				countryPhoneCode;
	private Collection<String>	creditCardMake;
	private Collection<String>	voidWords;


	@NotBlank
	@SafeHtml
	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(final String sysName) {
		this.sysName = sysName;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageEsp() {
		return this.welcomeMessageEsp;
	}

	public void setWelcomeMessageEsp(final String welcomeMessageEsp) {
		this.welcomeMessageEsp = welcomeMessageEsp;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@Pattern(regexp = "^[+][1-9]{0,2}")
	public String getCountryPhoneCode() {
		return this.countryPhoneCode;
	}

	public void setCountryPhoneCode(final String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getCreditCardMake() {
		return this.creditCardMake;
	}

	public void setCreditCardMake(final Collection<String> creditCardMake) {
		this.creditCardMake = creditCardMake;
	}

	@ElementCollection
	@EachNotBlank
	public Collection<String> getVoidWords() {
		return this.voidWords;
	}

	public void setVoidWords(final Collection<String> voidWords) {
		this.voidWords = voidWords;
	}

}
