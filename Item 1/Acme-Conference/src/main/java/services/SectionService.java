
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	@Autowired
	private SectionRepository	sectionRepository;


	//METODOS CRUD
	public Section create() {
		final Section res = new Section();
		res.setTitle("");
		res.setSummary("");
		final Collection<String> pictures = new ArrayList<>();
		res.setPictures(pictures);
		return res;
	}
	public Section findOne(final int idSection) {
		final Section res = this.sectionRepository.findOne(idSection);
		Assert.notNull(res, "La Section es nula - findOne");
		return res;
	}

	public Collection<Section> findAll() {
		final Collection<Section> res = this.sectionRepository.findAll();
		Assert.notNull(res, "La Collection<Section> es nula - findAll");
		return res;
	}
	public Section save(final Section section) {
		Assert.notNull(section, "La section es nula - save");
		final Section res = this.sectionRepository.save(section);
		return res;
	}
}
