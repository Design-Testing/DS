
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PanelRepository;

@Service
@Transactional
public class PanelService {

	@Autowired
	private PanelRepository	panelRepository;
}
