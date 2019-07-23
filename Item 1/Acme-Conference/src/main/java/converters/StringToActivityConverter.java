
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.PanelRepository;
import repositories.PresentationRepository;
import repositories.TutorialRepository;
import domain.Activity;

@Component
@Transactional
public class StringToActivityConverter implements Converter<String, Activity> {

	@Autowired
	private TutorialRepository		tutorialRepository;

	@Autowired
	private PanelRepository			panelRepository;

	@Autowired
	private PresentationRepository	presentationRepository;


	@Override
	public Activity convert(final String text) {

		Activity result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.presentationRepository.findOne(id);
				if (result == null)
					result = this.panelRepository.findOne(id);
				if (result == null)
					result = this.tutorialRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
