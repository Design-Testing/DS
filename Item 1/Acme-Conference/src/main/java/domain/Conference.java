
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends DomainEntity {

	//Atributos
	private String					title;
	private String					acronym;
	private String					venue;
	private Date					submission;
	private Date					notification;
	private Date					cameraReady;
	private Date					startDate;
	private Date					endDate;

	private String					summary;
	private Double					fee;
	private Boolean					isDraft;

	//Relaciones

	private Category				category;
	private Collection<Activity>	activities;


	@NotNull
	public Boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final Boolean isDraft) {
		this.isDraft = isDraft;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	@SafeHtml
	public String getAcronym() {
		return this.acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}
	@NotBlank
	@SafeHtml
	public String getVenue() {
		return this.venue;
	}

	public void setVenue(final String venue) {
		this.venue = venue;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Date submission) {
		this.submission = submission;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getNotification() {
		return this.notification;
	}

	public void setNotification(final Date notification) {
		this.notification = notification;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getCameraReady() {
		return this.cameraReady;
	}

	public void setCameraReady(final Date cameraReady) {
		this.cameraReady = cameraReady;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
	@NotBlank
	@SafeHtml
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}
	@NotNull
	@Min(0)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(final Collection<Activity> activities) {
		this.activities = activities;
	}

}
