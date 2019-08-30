
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PaperRepository;
import domain.Paper;

@Service
@Transactional
public class PaperService {

	@Autowired
	private PaperRepository	paperRepository;


	public Paper create() {
		final Paper res = new Paper();
		return res;
	}

	public Paper save(final Paper paper) {
		Assert.notNull(paper);
		Assert.isTrue(paper.getTitle().trim() != "");
		Assert.notNull(paper.getTitle());
		Assert.isTrue(paper.getSummary().trim() != "");
		Assert.notNull(paper.getSummary());
		Assert.isTrue(paper.getDocument().trim() != "");
		Assert.notNull(paper.getDocument());
		Assert.isTrue(!(paper.getAuthors().isEmpty()));
		Assert.notNull(paper.getAuthors());

		final Paper res = this.paperRepository.save(paper);
		Assert.notNull(res);

		return res;
	}

	public Paper findOne(final Integer paperId) {
		Assert.notNull(paperId);
		final Paper res = this.paperRepository.findOne(paperId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Paper> findAll() {
		Collection<Paper> res;
		res = this.paperRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public void delete(final Integer paperId) {
		Assert.notNull(paperId);
		this.paperRepository.delete(paperId);
	}

	public Collection<Paper> findByAuthorUAId(final int authorId) {
		Assert.isTrue(authorId != 0);
		final Collection<Paper> res = this.paperRepository.findByAuthorUAId(authorId);
		return res;
	}

}
