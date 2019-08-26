
package forms;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ReviewerForm extends ActorForm {

	private List<String>	keywords;


	@ElementCollection
	@EachNotBlank
	public List<String> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(final List<String> keywords) {
		this.keywords = keywords;
	}
}
