
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.RegisterRepository;
import domain.Register;

@Component
@Transactional
public class StringToRegisterConverter implements Converter<String, Register> {

	@Autowired
	private RegisterRepository	registerRepository;


	@Override
	public Register convert(final String text) {

		final Register result;
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
