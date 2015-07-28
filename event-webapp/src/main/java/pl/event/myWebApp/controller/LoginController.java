package pl.event.myWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController 
{
	@RequestMapping("/")
	public String showLoginPage(ModelMap map)
	{
		return "login-form";
	}
}
