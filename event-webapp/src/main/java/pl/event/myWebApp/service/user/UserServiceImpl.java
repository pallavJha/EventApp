package pl.event.myWebApp.service.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.event.base.user.User;
import pl.event.myApp.persistense.UserDao;

@Service
@Qualifier("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	UserDao userDao;

	@Override
	public User findUserById(Long id) {
		return userDao.findUserById(id);
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}

	@Override
	public String deleteUser(Long id) {
		return userDao.deleteUser(id);
	}

	@Override
	public void setUserToCurrentSession(UserDetails ud) {

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				ud, ud.getPassword(), ud.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	public String passwordToMd5Hash(String password) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}

		System.out.println(password + " converted to " + sb.toString());

		return sb.toString();
	}

}
