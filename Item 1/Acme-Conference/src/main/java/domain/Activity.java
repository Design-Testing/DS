
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity {

	private String				title;
	private Date				startMoment;
	private Integer				hours;
	private Integer				minutes;
	private String				room;
	private String				summary;
	private Collection<String>	attachments;

	private Collection<Actor>	speakers;


	@NotBlank
	@NotNull
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Range(min = 0)
	public Integer getHours() {
		return this.hours;
	}

	public void setHours(final Integer hours) {
		this.hours = hours;
	}

	@NotNull
	@Range(min = 0, max = 60)
	public Integer getMinutes() {
		return this.minutes;
	}

	public void setMinutes(final Integer minutes) {
		this.minutes = minutes;
	}

	@NotBlank
	@NotNull
	@SafeHtml
	public String getRoom() {
		return this.room;
	}

	public void setRoom(final String room) {
		this.room = room;
	}

	@NotBlank
	@NotNull
	@SafeHtml
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@ElementCollection
	@EachURL
	@EachNotBlank
	@NotNull
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	@Valid
	@NotNull
	@NotEmpty
	@ManyToMany
	public Collection<Actor> getSpeakers() {
		return this.speakers;
	}

	public void setSpeakers(final Collection<Actor> speakers) {
		this.speakers = speakers;
	}

}
