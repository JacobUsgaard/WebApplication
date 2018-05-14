package usgaard.jacob.web.app.service;

import org.springframework.stereotype.Service;

import usgaard.jacob.web.app.entity.User;
import usgaard.jacob.web.app.service.response.ServiceResponse;

@Service
public class UserService extends BaseService<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ServiceResponse<User> validate(User entity) {
		return new ServiceResponse<>(entity);
	}

}
