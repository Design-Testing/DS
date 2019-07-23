
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity {

	private String	spanish;
	private String	english;


	@NotBlank
	@SafeHtml
	public String getSpanish() {
		return this.spanish;
	}

	public void setSpanish(final String spanish) {
		this.spanish = spanish;
	}
	@NotBlank
	@SafeHtml
	public String getEnglish() {
		return this.english;
	}

	public void setEnglish(final String english) {
		this.english = english;
	}

}
