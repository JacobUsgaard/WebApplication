package usgaard.jacob.web.app.test.entity;

import org.springframework.stereotype.Component;

import usgaard.jacob.web.app.entity.User;

@Component
public class UserTest implements EntityTest<User>{

	@Override
	public User create() {
		User user = new User();
		user.setUsername("TEST_USER");
		user.setSalt("1234");
		user.setPassword("PASSWORD");
		return user;
	}

}
