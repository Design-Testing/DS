
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.PaperRepository;
import domain.Paper;

@Component
@Transactional
public class StringToPaperConverter implements Converter<String, Paper> {

	@Autowired
	private PaperRepository	paperRepository;


	@Override
	public Paper convert(final String text) {

		final Paper result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.paperRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
