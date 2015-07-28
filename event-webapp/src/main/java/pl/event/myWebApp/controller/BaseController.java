package pl.event.myWebApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.event.base.learning.Student;
import pl.event.myApp.persistense.Dao;
import pl.event.myApp.persistense.HibernateFTSService;

@Controller
@RequestMapping("/base")
@SuppressWarnings("unchecked")
public class BaseController {

	@Autowired
	@Qualifier("dao")
	Dao dao;

	@Autowired
	@Qualifier("hibernateFTSService")
	HibernateFTSService hibernateFTSService;

	@PersistenceContext
	EntityManager em;

	@RequestMapping("/")
	public String testCall() {
		return "index";
	}

	@RequestMapping("/new-student")
	public String newStudent() {
		return "student_form";
	}

	@ResponseBody
	@RequestMapping(value = "/save-student", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveStudent(Student student, ModelMap map) {
		System.out.println(student.getName());
		System.out.println(TransactionSynchronizationManager
				.isSynchronizationActive());
		em.persist(student);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/view-all-students")
	@Transactional(propagation = Propagation.REQUIRED)
	public String viewAllStudent(Student student, ModelMap map) {
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> m = new ArrayList<HashMap<String, String>>();
		List<Student> studentList = em.createQuery("SELECT s FROM Student s")
				.getResultList();
		for (Student s : studentList) {
			HashMap<String, String> tempMap = new HashMap<String, String>();
			tempMap.put("id", s.getId().toString());
			tempMap.put("name", s.getName());
			tempMap.put("bagColor", s.getSchoolBag().getColor());
			tempMap.put("subjects-length",
					String.valueOf(s.getSubjects().size()));
			m.add(tempMap);
		}
		try {
			return mapper.writeValueAsString(m);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping("/search-page")
	public String searchWithinStudent() {
		return "searchPage";
	}

	@ResponseBody
	@RequestMapping(value = "/search-main")
	@Transactional(propagation = Propagation.REQUIRED)
	public String fullTextSearch(@RequestParam("toSearch") String toSearch,
			ModelMap map) {

		List<Object> searchResults = hibernateFTSService.search(Student.class,
				toSearch, "name", "schoolBag.color");
		System.out.println(searchResults);
		try {
			return new ObjectMapper().writeValueAsString(searchResults);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
