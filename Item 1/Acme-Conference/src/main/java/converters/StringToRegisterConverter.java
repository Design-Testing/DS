
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.RegistrationRepository;
import domain.Registration;

@Component
@Transactional
public class StringToRegisterConverter implements Converter<String, Registration> {

	@Autowired
	private RegistrationRepository	registerRepository;


	@Override
	public Registration convert(final String text) {

		final Registration result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.registerRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
