package pl.event.base.learning;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

@Entity
public class SchoolBag {

	@Id
	@GeneratedValue
	private Long id;

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String color;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String colour) {
		this.color = colour;
	}

}
