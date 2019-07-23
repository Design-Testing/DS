
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	ConferenceRepository	conferenceRepository;


	public Collection<Conference> findConferences(final String keyword, final String categoryName, final Date fromDate, final Date toDate, final Double maximumFee) {
		final Collection<Conference> res = this.conferenceRepository.findConferences(keyword, categoryName, fromDate, toDate, maximumFee);
		Assert.notNull(res);
		return res;
	}

}
