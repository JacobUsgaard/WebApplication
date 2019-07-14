package usgaard.jacob.web.app.test.controller.data;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import usgaard.jacob.web.app.controller.data.DeleteDataController;
import usgaard.jacob.web.app.entity.BaseEntity;

@Transactional
public interface DeleteDataControllerTest<Entity extends BaseEntity> extends DataControllerTest<Entity> {

	@Test
	public default void deleteTest() throws Exception {
		Entity expected = getBaseService().saveOrUpdate(getEntityTest().create()).getData();

		getMockMvc()
				.perform(
						delete(getDataController().getFullRequestMapping() + "/" + DeleteDataController.REQUEST_MAPPING_DELETE)
								.content(getObjectMapper().writeValueAsString(expected))
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id").value(expected.getId()))
				.andDo(print())
				.andDo(log());

		assertNull(getBaseService().findById(expected.getId()).getData());
	}
}
