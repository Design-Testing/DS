
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private List<String>	keywords;


	// @EachSafeHtml -> Revisar
	@ElementCollection
	@EachNotBlank
	public List<String> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(final List<String> keywords) {
		this.keywords = keywords;
	}

}
