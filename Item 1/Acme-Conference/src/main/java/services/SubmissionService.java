
package services;

import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;


	public Submission create() {
		final Submission res = new Submission();
		res.setTicker("ABC-1D2E");
		res.setMoment(new Date());
		res.setStatus("UNDER-REVIEWED");
		return res;
	}

	public Submission save(final Submission submission, final Conference conference, final Author author, final Paper cameraReadyPaper, final Paper reviewPaper) {
		Assert.notNull(submission);
		Assert.notNull(conference);
		Assert.notNull(author);
		Assert.notNull(cameraReadyPaper);
		Assert.notNull(reviewPaper);

		Assert.isTrue(Pattern.matches("yyyy-MM-dd HH:mm", submission.getMoment().toString()));
		Assert.isTrue(Pattern.matches("^([A-Z]{3}-[0-9A-Z]{4})$", submission.getTicker()));
		Assert.isTrue(Pattern.matches("^(UNDER-REVIEWED|ACCEPTED|REJECTED)$", submission.getStatus()));

		submission.setConference(conference);
		submission.setAuthor(author);
		submission.setCameraReadyPaper(cameraReadyPaper);
		submission.setReviewPaper(reviewPaper);

		final Submission res = this.submissionRepository.save(submission);
		Assert.notNull(res);

		return res;
	}

	public Submission findOne(final Integer submissionId) {
		Assert.notNull(submissionId);
		final Submission res = this.submissionRepository.findOne(submissionId);
		Assert.notNull(res);

		return res;
	}

	public Collection<Submission> findAll() {
		final Collection<Submission> res = this.submissionRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void delete(final Integer submissionId) {
		Assert.notNull(submissionId);
		this.submissionRepository.delete(submissionId);
	}
}
