
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;


	public Comment create() {
		final Comment res = new Comment();
		return res;
	}

	public Comment save(final Comment comment) {
		Comment res = new Comment();
		Assert.notNull(comment);
		Assert.isTrue(comment.getTitle().trim() != "");
		Assert.isTrue(comment.getText().trim() != "");
		Assert.notNull(comment.getMoment());

		res = this.commentRepository.save(comment);
		Assert.notNull(res);
		return res;
	}

	public Comment findOne(final Integer commentId) {
		Comment res;
		Assert.notNull(commentId);
		res = this.commentRepository.findOne(commentId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> res;
		res = this.commentRepository.findAll();
		Assert.notNull(res);
		return res;

	}

	public void delete(final Integer commentId) {
		Assert.notNull(commentId);
		this.commentRepository.delete(commentId);
	}
}
