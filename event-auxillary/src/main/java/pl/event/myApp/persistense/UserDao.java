package pl.event.myApp.persistense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import pl.event.base.user.User;

@Repository
@Qualifier("userDao")
public class UserDao {

	@Autowired
	@Qualifier("dao")
	Dao dao;

	private Class<User> clazz = User.class;

	public User findUserById(Long id) {
		return dao.find(User.class, id);

	}

	public User findUserByUsername(String username) {
		List<User> userList = dao.searchBaseOnFieldAndValue(clazz, "username",
				username);
		try {
			return userList.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	public String deleteUser(Long id) {
		User user = findUserById(id);
		dao.delete(user);
		return user.getUsername();
	}
}
