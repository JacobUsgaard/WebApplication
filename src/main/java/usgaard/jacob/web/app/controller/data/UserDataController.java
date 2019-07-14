package usgaard.jacob.web.app.controller.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usgaard.jacob.web.app.entity.User;

/**
 * Rest controller for {@link User} data.
 */
@RestController
@RequestMapping(UserDataController.REQUEST_MAPPING)
public class UserDataController
		extends BaseDataController<User>
		implements FindByIdDataController<User>, DeleteDataController<User>, DeleteAllDataController<User>, DeleteByIdDataController<User>, SaveOrUpdateDataController<User>, SaveOrUpdateAllDataController<User> {

	public static final String REQUEST_MAPPING = "/user";

	@Override
	public String getRequestMapping() {
		return REQUEST_MAPPING;
	}

}
