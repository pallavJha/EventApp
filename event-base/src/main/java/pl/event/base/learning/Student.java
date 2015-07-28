package pl.event.base.learning;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Indexed
@Entity
public class Student {

	@Id
	@GeneratedValue
	private Long id;

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Subject> subjects;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@IndexedEmbedded
	private SchoolBag schoolBag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public SchoolBag getSchoolBag() {
		return schoolBag;
	}

	public void setSchoolBag(SchoolBag schoolBag) {
		this.schoolBag = schoolBag;
	}
	
}
