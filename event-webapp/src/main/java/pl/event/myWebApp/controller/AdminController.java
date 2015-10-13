package pl.event.myWebApp.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping
	public String showAdminPage(Principal principal) {
		System.out.println("***************************************\n\n\n");
		
		System.out.println(SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities());
		System.out.println(principal.getName());
		System.out.println("\n\n\n***************************************");
		return "adminPage";
	}
}
