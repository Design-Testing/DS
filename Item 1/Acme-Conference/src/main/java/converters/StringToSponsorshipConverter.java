
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Component
@Transactional
public class StringToSponsorshipConverter implements Converter<String, Sponsorship> {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	@Override
	public Sponsorship convert(final String text) {
		Sponsorship result;
		int id;

		if (text == null)
			result = null;
		else
			try {
				if (StringUtils.isEmpty(text))
					result = null;
				else {
					id = Integer.valueOf(text);
					result = this.sponsorshipRepository.findOne(id);
				}
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		return result;
	}
}
