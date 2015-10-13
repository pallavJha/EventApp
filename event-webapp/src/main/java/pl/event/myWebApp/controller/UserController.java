package pl.event.myWebApp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.event.base.user.Role;
import pl.event.base.user.User;
import pl.event.myApp.persistense.Dao;
import pl.event.myApp.persistense.HibernateFTSService;
import pl.event.myApp.persistense.UserDao;

@Controller
@RequestMapping("/User")
public class UserController {

	@Autowired
	@Qualifier("dao")
	Dao dao;

	@Autowired
	@Qualifier("hibernateFTSService")
	HibernateFTSService hibernateFTSService;

	@PersistenceContext
	EntityManager em;

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	
	@ResponseBody
	@RequestMapping(value = "/save-user", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveStudent(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("adminCheck") boolean adminCheck, ModelMap map) {
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setActive(true);
		Role newRole = new Role();
		newRole.setAuthority("ROLE_USER");
		List<Role> roleList = new ArrayList<Role>();
		if (adminCheck) {
			Role adminRole = new Role();
			adminRole.setAuthority("ROLE_ADMIN");
			roleList.add(adminRole);
		}
		roleList.add(newRole);
		newUser.setRoles(roleList);
		em.persist(newUser);
		return "success";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRED)
	public String getAllUsers(ModelMap map) {
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> m = new ArrayList<HashMap<String, String>>();
		List<User> userList = em.createQuery("SELECT u FROM User u")
				.getResultList();
		for (User u : userList) {
			HashMap<String, String> tempMap = new HashMap<String, String>();
			tempMap.put("username", u.getUsername());
			m.add(tempMap);
		}
		try {
			return mapper.writeValueAsString(m);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getUserName", method=RequestMethod.GET)
	public String getUserName(Principal principal,ModelMap map){
		return principal.getName();
	}
	
	@RequestMapping(value="/error/{code}")
	public String getErrorPage(@PathVariable("code") String code,ModelMap map, Principal principal){
		if(code.equals(String.valueOf(HttpServletResponse.SC_FORBIDDEN))){
			return "error403";
		}
		return "error";
	}
}
