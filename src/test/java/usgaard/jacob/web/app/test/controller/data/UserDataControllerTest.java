package usgaard.jacob.web.app.test.controller.data;

import org.springframework.stereotype.Component;

import usgaard.jacob.web.app.entity.User;

@Component
public class UserDataControllerTest extends DataControllerBaseTest<User>
		implements FindByIdDataControllerTest<User>, DeleteDataControllerTest<User>, DeleteAllDataControllerTest<User>, DeleteByIdDataControllerTest<User>, SaveOrUpdateAllDataControllerTest<User>, SaveOrUpdateDataControllerTest<User> {

}
