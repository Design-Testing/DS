
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PresentationRepository;

@Service
@Transactional
public class PresentationService extends ActivityService {

	@Autowired
	private PresentationRepository	presentationRepository;

}
