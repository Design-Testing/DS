
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private String			title;
	private String			text;
	private Date			moment;

	// Relationships
	private Actor			author;

	private Conference		conference;
	private Presentation	presentation;
	private Panel			panel;
	private Tutorial		tutorial;
	private Report			report;


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
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
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

	@Valid
	@ManyToOne(optional = true)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@Valid
	@ManyToOne(optional = true)
	public Tutorial getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(final Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	@Valid
	@ManyToOne(optional = true)
	public Panel getPanel() {
		return this.panel;
	}

	public void setPanel(final Panel panel) {
		this.panel = panel;
	}

	@Valid
	@ManyToOne(optional = true)
	public Presentation getPresentation() {
		return this.presentation;
	}

	public void setPresentation(final Presentation presentation) {
		this.presentation = presentation;
	}

	@Valid
	@ManyToOne(optional = true)
	public Actor getAuthor() {
		return this.author;
	}

	public void setAuthor(final Actor author) {
		this.author = author;
	}

	@Valid
	@ManyToOne(optional = true)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

}
