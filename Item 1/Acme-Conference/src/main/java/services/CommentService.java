/*
 * CommentService.java
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Conference;
import domain.Panel;
import domain.Report;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private ReportService		reportService;


	public Comment create() {
		final Comment res = new Comment();
		final Date moment = new Date(System.currentTimeMillis() - 1000);
		res.setMoment(moment);
		return res;
	}

	public Comment create(final String entity, final int entityId) {
		final Comment comment = this.create();
		switch (entity) {
		case "activity":
			comment.setActivity(this.activityService.findOne(entityId));
			break;
		case "conference":
			comment.setConference(this.conferenceService.findOne(entityId));
			break;
		case "report":
			comment.setReport(this.reportService.findOne(entityId));
			break;
		// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo

		//		 case "quolet":
		//		 comment.setQuolet(this.quoletService.findOne(entityId));
		//		 break;

		default:
			throw new IllegalArgumentException("Invalid entity relationship while creating the comment");
		}
		return comment;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(this.checkAssociation(comment));
		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication authentication = context.getAuthentication();
		final Object principal = authentication.getPrincipal();
		if (principal != "anonymousUser")
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
		aux.add(comment.getReport());
		aux.add(comment.getActivity());
		// Para futuras relaciones de la entidad comentario con una nueva entidad de nombre Quolet, agregar una nueva linea:
		// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo
		// aux.add(comment.getQuolet());

		for (final Object o : aux)
			if (o != null)
				acum++;
		return acum == 1;
	}

	/**
	 * Este metodo sirve para dado un comentario, poder obtener a que entidad se esta comentando y cual es el id de esa entidad
	 * 
	 * @param comment
	 *            Comentario cuya relacion va a estudiarse
	 * @return Se devuelve una lista en la que la primera posicion alberga un String con el nombre de la entidad con la que el comentario esta relacionado y en la segunda posicion el id de dicha entidad (se comporta como una tupla).
	 * @author a8081
	 * */
	public List<Object> findRelationEntity(final Comment comment) {
		final List<Object> res = new ArrayList<>();
		final Collection<Object> aux = new ArrayList<>();

		aux.add(comment.getConference());
		aux.add(comment.getReport());
		aux.add(comment.getActivity());
		// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo
		// aux.add(comment.getQuolet());

		for (final Object o : aux)
			if (o != null) {
				if (o instanceof Conference) {
					res.add("conference");
					res.add(comment.getConference().getId());
				} else if (o instanceof Panel) {
					res.add("activity");
					res.add(comment.getActivity().getId());
				} else if (o instanceof Report) {
					res.add("report");
					res.add(comment.getReport().getId());
				}

				// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo
				//	else if (o instanceof Quolet) {
				//		res.add("quolet");
				//		res.add(comment.getQuolet().getId());
				//	}
				break;
			}

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

	public Collection<Comment> findByConference(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);
		final Collection<Comment> res = this.commentRepository.findByConference(conferenceId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Comment> findByActivity(final int id) {
		Assert.isTrue(id != 0);
		final Collection<Comment> res = this.commentRepository.findByActivity(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Comment> findByReport(final int id) {
		Assert.isTrue(id != 0);
		final Actor principal = this.actorService.findByPrincipal();
		final Collection<Comment> res = this.commentRepository.findByReport(id, principal.getId());
		Assert.notNull(res);
		return res;
	}

	// TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo

	//	public Collection<Comment> findByQuolet(int id) {
	//		Assert.isTrue(id != 0);
	//		final Collection<Comment> res = this.commentRepository.findByQuolet(id);
	//		Assert.notNull(res);
	//		return res;
	//	}
}
