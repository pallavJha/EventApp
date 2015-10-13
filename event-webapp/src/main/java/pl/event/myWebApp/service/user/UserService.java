package pl.event.myWebApp.service.user;

import java.security.NoSuchAlgorithmException;

import pl.event.base.user.User;

public interface UserService {

	User findUserById(Long id);

	User findUserByUsername(String username);

	String deleteUser(Long id);

	void setUserToCurrentSession(
			org.springframework.security.core.userdetails.UserDetails ud);

	String passwordToMd5Hash(String password) throws NoSuchAlgorithmException;
}
