
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Registration;

@Component
@Transactional
public class RegisterToStringConverter implements Converter<Registration, String> {

	@Override
	public String convert(final Registration register) {

		String result;

		if (register == null)
			result = null;
		else
			result = String.valueOf(register.getId());

		return result;

	}

}
