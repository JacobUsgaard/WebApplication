package usgaard.jacob.web.app.test.controller.data;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import usgaard.jacob.web.app.controller.data.SaveOrUpdateDataController;
import usgaard.jacob.web.app.entity.BaseEntity;

@Transactional
public interface SaveOrUpdateDataControllerTest<Entity extends BaseEntity> extends DataControllerTest<Entity> {

	@Test
	public default void saveOrUpdatePut() throws Exception {
		Entity expected = getEntityTest().create();

		getMockMvc()
				.perform(
						put(getDataController().getFullRequestMapping() + "/" + SaveOrUpdateDataController.REQUEST_METHOD_SAVE_OR_UDPATE)
								.content(getObjectMapper().writeValueAsString(expected))
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id").exists())
				.andDo(log());
	}

	@Test
	public default void saveOrUpdatePost() throws Exception {
		Entity expected = getEntityTest().create();

		getMockMvc()
				.perform(
						post(getDataController().getFullRequestMapping() + "/" + SaveOrUpdateDataController.REQUEST_METHOD_SAVE_OR_UDPATE)
								.content(getObjectMapper().writeValueAsString(expected))
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id").exists())
				.andDo(log());
	}

}
