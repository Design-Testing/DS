
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyword;
	private String					categoryName;
	private Date					fromDate;
	private Date					toDate;
	private Double					maximumFee;

	// Relationships
	private Collection<Conference>	conferences;


	@SafeHtml
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@SafeHtml
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(final Date fromDate) {
		this.fromDate = fromDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(final Date toDate) {
		this.toDate = toDate;
	}

	@Min(0)
	public Double getMaximumFee() {
		return this.maximumFee;
	}

	public void setMaximumFee(final Double maximumFee) {
		this.maximumFee = maximumFee;
	}

	@ManyToMany
	public Collection<Conference> getConferences() {
		return this.conferences;
	}

	public void setConferences(final Collection<Conference> conferences) {
		this.conferences = conferences;
	}

}
