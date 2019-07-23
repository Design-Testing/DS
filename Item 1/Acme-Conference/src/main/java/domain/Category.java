
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		titleEn;
	private String		titleEs;
	private Category	father;


	@NotBlank
	@SafeHtml
	public String getTitleEn() {
		return this.titleEn;
	}

	public void setTitleEn(final String titleEn) {
		this.titleEn = titleEn;
	}

	@NotBlank
	@SafeHtml
	public String getTitleEs() {
		return this.titleEs;
	}

	public void setTitleEs(final String titleEs) {
		this.titleEs = titleEs;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getFather() {
		return this.father;
	}

	public void setFather(final Category father) {
		this.father = father;
	}

}
