package usgaard.jacob.web.app.test.controller.data;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import usgaard.jacob.web.app.controller.data.SaveOrUpdateAllDataController;
import usgaard.jacob.web.app.entity.BaseEntity;

@Transactional
public interface SaveOrUpdateAllDataControllerTest<Entity extends BaseEntity> extends DataControllerTest<Entity> {

	@Test
	public default void saveOrUpdateAllPut() throws Exception {
		Collection<Entity> entities = getEntityTest().createAll(10);

		getMockMvc()
				.perform(
						put(getDataController().getFullRequestMapping() + "/" + SaveOrUpdateAllDataController.REQUEST_METHOD_SAVE_OR_UDPATE_ALL)
								.content(getObjectMapper().writeValueAsString(entities))
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data").isArray())
				.andDo(log());
	}

	@Test
	public default void saveOrUpdateAllPost() throws Exception {
		Collection<Entity> entities = getEntityTest().createAll(10);

		getMockMvc()
				.perform(
						post(getDataController().getFullRequestMapping() + "/" + SaveOrUpdateAllDataController.REQUEST_METHOD_SAVE_OR_UDPATE_ALL)
								.content(getObjectMapper().writeValueAsString(entities))
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data").isArray())
				.andDo(print())
				.andDo(log());
	}

}
