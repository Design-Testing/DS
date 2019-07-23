
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	holderName;
	private String	make;
	private String	number;
	private Integer	expirationMonth;
	private Integer	expirationYear;
	private String	cvv;


	@NotBlank
	@SafeHtml
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	@SafeHtml
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@Column(unique = true)
	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Range(min = 0, max = 99)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Pattern(regexp = "^([0-9]){3}$")
	public String getCvv() {
		return this.cvv;
	}

	public void setCvv(final String cvv) {
		this.cvv = cvv;
	}

	//	<bean id="creditCard1" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 1" />
	//	<property name="make" value="VISA" />
	//	<property name="number" value="4716477920082572" />
	//	<property name="expirationMonth" value="06" />
	//	<property name="expirationYear" value="19" />
	//	<property name="cvv" value="163" />
	//</bean>
	//
	//<bean id="creditCard2" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 2" />
	//	<property name="make" value="MASTER" />
	//	<property name="number" value="5498128346540526" />
	//	<property name="expirationMonth" value="10" />
	//	<property name="expirationYear" value="20" />
	//	<property name="cvv" value="728" />
	//</bean>
	//
	//<bean id="creditCard3" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 3" />
	//	<property name="make" value="AMEX" />
	//	<property name="number" value="375278545368168" />
	//	<property name="expirationMonth" value="06" />
	//	<property name="expirationYear" value="19" />
	//	<property name="cvv" value="533" />
	//</bean>
	//
	//<bean id="creditCard4" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 4" />
	//	<property name="make" value="VISA" />
	//	<property name="number" value="4532787155338743" />
	//	<property name="expirationMonth" value="10" />
	//	<property name="expirationYear" value="19" />
	//	<property name="cvv" value="266" />
	//</bean>
	//
	//<bean id="creditCard5" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 5" />
	//	<property name="make" value="VISA" />
	//	<property name="number" value="4716699361876929" />
	//	<property name="expirationMonth" value="02" />
	//	<property name="expirationYear" value="19" />
	//	<property name="cvv" value="885" />
	//</bean>
	//
	//
	//<bean id="creditCard6" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 6" />
	//	<property name="make" value="VISA" />
	//	<property name="number" value="4231348143458624" />
	//	<property name="expirationMonth" value="11" />
	//	<property name="expirationYear" value="22" />
	//	<property name="cvv" value="837" />
	//</bean>
	//
	//<bean id="creditCard7" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 7" />
	//	<property name="make" value="VISA" />
	//	<property name="number" value="4294148159742547" />
	//	<property name="expirationMonth" value="11" />
	//	<property name="expirationYear" value="20" />
	//	<property name="cvv" value="988" />
	//</bean>
	//
	//<bean id="creditCard8" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 8" />
	//	<property name="make" value="MASTERCARD" />
	//	<property name="number" value="5547165664775350" />
	//	<property name="expirationMonth" value="11" />
	//	<property name="expirationYear" value="20" />
	//	<property name="cvv" value="475" />
	//</bean>
	//
	//<bean id="creditCard9" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 9" />
	//	<property name="make" value="VISA" />
	//	<property name="number" value="4410435734979051" />
	//	<property name="expirationMonth" value="03" />
	//	<property name="expirationYear" value="27" />
	//	<property name="cvv" value="941" />
	//</bean>
	//
	//<bean id="creditCard10" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 10" />
	//	<property name="make" value="MASTERCARD" />
	//	<property name="number" value="5316710759043864" />
	//	<property name="expirationMonth" value="01" />
	//	<property name="expirationYear" value="26" />
	//	<property name="cvv" value="408" />
	//</bean>
	//
	//
	//<bean id="creditCard13" class="domain.CreditCard">
	//	<property name="holderName" value="HolderName 11" />
	//	<property name="make" value="MASTERCARD" />
	//	<property name="number" value="5384948404521051" />
	//	<property name="expirationMonth" value="01" />
	//	<property name="expirationYear" value="20" />
	//	<property name="cvv" value="906" />
	//</bean>

}
