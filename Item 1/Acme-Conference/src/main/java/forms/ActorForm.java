
package forms;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class ActorForm extends DomainEntity {

	private String			name;
	private String			middleName;
	private List<String>	surname;
	private String			photo;
	private String			email;
	private String			phone;
	private String			address;

	//Relational attributes
	private String			userAccountuser;
	private String			userAccountpassword;


	@Size(min = 5, max = 32)
	public String getUserAccountuser() {
		return this.userAccountuser;
	}

	public void setUserAccountuser(final String userAccountuser) {
		this.userAccountuser = userAccountuser;
	}
	@Size(min = 5, max = 32)
	public String getUserAccountpassword() {
		return this.userAccountpassword;
	}

	public void setUserAccountpassword(final String userAccountpassword) {
		this.userAccountpassword = userAccountpassword;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@ElementCollection
	@NotEmpty
	@EachNotBlank
	public List<String> getSurname() {
		return this.surname;
	}

	public void setSurname(final List<String> surname) {
		this.surname = surname;
	}

	@URL
	@SafeHtml
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Column(unique = true)
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

}
