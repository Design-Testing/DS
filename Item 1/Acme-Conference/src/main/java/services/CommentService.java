/*
 * CommentService.java
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	private ActorService		actorService;


	public Comment create() {
		return new Comment();
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(this.checkAssociation(comment));
		if (SecurityContextHolder.getContext().getAuthentication() != null)
			comment.setAuthor(this.actorService.findByPrincipal());
		final Date moment = new Date(System.currentTimeMillis() - 1000);
		comment.setMoment(moment);
		final Comment res = this.commentRepository.save(comment);
		Assert.notNull(res);
		return res;
	}

	/**
	 * Este metodo recorre todas las relaciones que comment tiene con otras entidades a comentar y comprueba que todas sean nulas menos una. Esto se hace para comprobar que la relación que se construye es correcta.
	 * 
	 * @param comment
	 *            El comentario a evaluar
	 * @return Devuelve un boolean que es true si las relaciones del comment pasado como parametro son correctas y false en caso contrario.
	 * */
	private boolean checkAssociation(final Comment comment) {
		int acum = 0;
		final Collection<Object> aux = new ArrayList<>();

		aux.add(comment.getConference());
		aux.add(comment.getPanel());
		aux.add(comment.getPresentation());
		aux.add(comment.getReport());
		aux.add(comment.getTutorial());
		// Para futuras relaciones de la entidad comentario con una nueva entidad de nombre 'EntityName', agregar una nueva linea:
		// aux.add(comment.get'EntityName'());

		for (final Object o : aux)
			if (o != null)
				acum++;
		return acum == 1;
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

	public Collection<Comment> findByPresentation(final int activityId) {
		Assert.isTrue(activityId != 0);
		final Collection<Comment> res = this.commentRepository.findByPresentation(activityId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Comment> findByPanel(final int activityId) {
		Assert.isTrue(activityId != 0);
		final Collection<Comment> res = this.commentRepository.findByPanel(activityId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Comment> findByTutorial(final int activityId) {
		Assert.isTrue(activityId != 0);
		final Collection<Comment> res = this.commentRepository.findByTutorial(activityId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Comment> findByConference(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		final Collection<Comment> res = this.commentRepository.findByConference(conferenceId);
		Assert.notNull(res);
		return res;
	}
}
