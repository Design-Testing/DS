
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Register;

@Component
@Transactional
public class RegisterToStringConverter implements Converter<Register, String> {

	@Override
	public String convert(final Register register) {

		String result;

		if (register == null)
			result = null;
		else
			result = String.valueOf(register.getId());

		return result;

	}

}
