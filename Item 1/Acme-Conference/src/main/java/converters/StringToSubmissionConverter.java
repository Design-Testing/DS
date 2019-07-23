
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SubmissionRepository;
import domain.Submission;

@Component
@Transactional
public class StringToSubmissionConverter implements Converter<String, Submission> {

	@Autowired
	private SubmissionRepository	submissionRepository;


	@Override
	public Submission convert(final String text) {
		Submission result;
		int id;

		if (text == null)
			result = null;
		else
			try {
				if (StringUtils.isEmpty(text))
					result = null;
				else {
					id = Integer.valueOf(text);
					result = this.submissionRepository.findOne(id);
				}
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		return result;
	}
}
