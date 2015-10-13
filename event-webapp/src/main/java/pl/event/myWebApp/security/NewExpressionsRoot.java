package pl.event.myWebApp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import pl.event.myApp.persistense.UserDao;

public class NewExpressionsRoot extends WebSecurityExpressionRoot {

	UserDao userDao;

	public NewExpressionsRoot(Authentication a, FilterInvocation fi, UserDao u) {
		super(a, fi);
		this.userDao = u;
	}

	public boolean isOver18() {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) this
				.getPrincipal();
		String username = user.getUsername();
		if (username != null) {
			int age = userDao.findUserByUsername(username).getAge();
			return age > 18;
		} else {
			return false;
		}
	}

}
