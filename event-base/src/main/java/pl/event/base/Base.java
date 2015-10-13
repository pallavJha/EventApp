package pl.event.base;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.DiscriminatorOptions;

@DiscriminatorOptions(force = true)
@MappedSuperclass
public abstract class Base {
	@Id
	@GeneratedValue
	private Long id;

	private String termName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}
}
