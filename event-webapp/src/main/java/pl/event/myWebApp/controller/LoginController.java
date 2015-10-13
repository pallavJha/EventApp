package pl.event.myWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(
			@RequestParam(value = "error", required = false) Boolean error,
			ModelMap map) {
		if (error != null && error) {
			map.put("error", error);
		}
		return "login-form";
	}

	@RequestMapping("/logout")
	public String showLogoutPage(ModelMap map) {
		return "login-form";
	}
}
