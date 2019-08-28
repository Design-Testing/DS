
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	private String		ticker;
	private Date		moment;
	private String		status;
	private Boolean		isNotified;

	// Relationships
	private Paper		cameraReadyPaper;
	private Paper		reviewPaper;

	private Author		author;
	private Conference	conference;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([A-Z]{3}-[0-9A-Z]{4})||([a-z]{3}-[0-9A-Z]{4})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^(UNDER-REVIEWED|ACCEPTED|REJECTED)$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Valid
	@ManyToOne(optional = true)
	public Paper getCameraReadyPaper() {
		return this.cameraReadyPaper;
	}

	public void setCameraReadyPaper(final Paper cameraReadyPaper) {
		this.cameraReadyPaper = cameraReadyPaper;
	}

	@Valid
	@ManyToOne(optional = false)
	public Paper getReviewPaper() {
		return this.reviewPaper;
	}

	public void setReviewPaper(final Paper reviewPaper) {
		this.reviewPaper = reviewPaper;
	}

	@Valid
	@ManyToOne(optional = false)
	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(final Author author) {
		this.author = author;
	}

	@Valid
	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@NotNull
	public Boolean getIsNotified() {
		return this.isNotified;
	}

	public void setIsNotified(final Boolean isNotified) {
		this.isNotified = isNotified;
	}

}
