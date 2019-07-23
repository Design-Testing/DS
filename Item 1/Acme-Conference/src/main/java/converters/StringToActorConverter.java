
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.AdministratorRepository;
import repositories.AuthorRepository;
import repositories.ReviewerRepository;
import repositories.SponsorRepository;
import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private SponsorRepository		sponsorRepository;

	@Autowired
	private ReviewerRepository		reviewerRepository;

	@Autowired
	private AuthorRepository		authorRepository;


	@Override
	public Actor convert(final String text) {

		Actor result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.authorRepository.findOne(id);
				if (result == null)
					result = this.administratorRepository.findOne(id);
				if (result == null)
					result = this.reviewerRepository.findOne(id);
				if (result == null)
					result = this.sponsorRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
