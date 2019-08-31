
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class AdminForm extends DomainEntity {

	private String	name;
	private String	middleName;
	private String	surname;
	private String	photo;
	private String	email;
	private String	phone;
	private String	address;

	//Relational attributes
	private String	userAccountuser;
	private String	userAccountpassword;


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

	@NotBlank
	@SafeHtml
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
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

	// ^[\\w]+@((?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,3}){0,1}$||^((([\\w]\\s)*[\\w])+\\s<[\\w]+@((?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,3}){0,1}>)$

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]+@((?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+){0,1}$||^((([a-zA-Z0-9]\\s)*[a-zA-Z0-9])+\\s<[a-zA-Z0-9]+@((?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+){0,1}>)$")
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
