package pl.event.myWebApp.service.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.event.base.user.Role;
import pl.event.base.user.User;
import pl.event.myApp.persistense.UserDao;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	@Qualifier("userService")
	UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userService.findUserByUsername(username);
		if (user != null) {
			String password = user.getPassword();
			boolean enabled = user.getActive();
			boolean accountNonExpired = user.getActive();
			boolean credentialsNonExpired = user.getActive();
			boolean accountNonLocked = user.getActive();

			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (Role r : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(r.getAuthority()));
			}
			org.springframework.security.core.userdetails.User securedUser = new org.springframework.security.core.userdetails.User(
					username, password, enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked, authorities);
			return securedUser;
		} else {
			throw new UsernameNotFoundException(
					"Unable to find user with username provided!!");
		}
	}
}
