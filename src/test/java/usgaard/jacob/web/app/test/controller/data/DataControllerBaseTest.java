package usgaard.jacob.web.app.test.controller.data;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import usgaard.jacob.web.app.controller.data.BaseDataController;
import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.BaseService;
import usgaard.jacob.web.app.test.BaseTest;
import usgaard.jacob.web.app.test.entity.EntityTest;

public abstract class DataControllerBaseTest<Entity extends BaseEntity> extends BaseTest<Entity> implements DataControllerTest<Entity> {

	@Autowired
	protected BaseService<Entity> baseService;

	@Autowired
	protected BaseDataController<Entity> baseDataController;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;
	
	protected final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Override
	public MockMvc getMockMvc() {
		return mockMvc;
	}

	@Override
	public BaseDataController<Entity> getDataController() {
		return baseDataController;
	}

	@Override
	public EntityTest<Entity> getEntityTest() {
		return entityTest;
	}

	@Override
	public BaseService<Entity> getBaseService() {
		return baseService;
	}
	
	@Override
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
