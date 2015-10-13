package pl.event.myWebApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;
import pl.event.base.user.Role;
import pl.event.base.user.User;
import pl.event.myWebApp.service.user.UserService;

@Controller
@RequestMapping("/signup")
@Transactional(propagation = Propagation.REQUIRED)
public class SignUpController {

	@PersistenceContext
	EntityManager em;

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String createNewUserWithSignUp(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "adminCheck", defaultValue = "false") boolean adminCheck,
			@RequestParam(value = "age", defaultValue = "15") Integer age)
			throws NoSuchAlgorithmException {

		User newUser = new User();

		newUser.setUsername(username);

		password = userService.passwordToMd5Hash(password);
		newUser.setPassword(password);

		newUser.setActive(true);
		newUser.setAge(age);

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
		em.flush();

		UserDetails ud = userDetailsService.loadUserByUsername(username);

		userService.setUserToCurrentSession(ud);

		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put("u-d", Boolean.TRUE.toString());
		myMap.put("access-to-index", Boolean.TRUE.toString());

		String jsonHarvey = new JSONSerializer().serialize(myMap);

		return jsonHarvey;
	}

}
