package usgaard.jacob.web.app.controller.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usgaard.jacob.web.app.entity.User;

/**
 * Rest controller for {@link User} data.
 * 
 * @author Jacob Usgaard
 *
 */
@RestController
@RequestMapping("user")
public class UserDataController extends BaseDataController<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
