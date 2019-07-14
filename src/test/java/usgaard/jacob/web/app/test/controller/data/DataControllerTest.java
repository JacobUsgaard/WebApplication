package usgaard.jacob.web.app.test.controller.data;

import javax.transaction.Transactional;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import usgaard.jacob.web.app.controller.data.BaseDataController;
import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.BaseService;
import usgaard.jacob.web.app.test.entity.EntityTest;

@Transactional
public interface DataControllerTest<Entity extends BaseEntity> {

	public MockMvc getMockMvc();

	public BaseDataController<Entity> getDataController();

	public EntityTest<Entity> getEntityTest();

	public BaseService<Entity> getBaseService();
	
	public ObjectMapper getObjectMapper();
	
	public Class<Entity> getEntityClass();
}
