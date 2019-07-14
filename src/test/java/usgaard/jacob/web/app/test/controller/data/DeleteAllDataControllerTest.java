package usgaard.jacob.web.app.test.controller.data;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import usgaard.jacob.web.app.controller.data.DeleteAllDataController;
import usgaard.jacob.web.app.entity.BaseEntity;

@Transactional
public interface DeleteAllDataControllerTest<Entity extends BaseEntity> extends DataControllerTest<Entity> {

	@Test
	public default void deleteAllTest() throws Exception {
		Collection<Entity> expected = getBaseService().saveOrUpdateAll(getEntityTest().createAll(10)).getData();

		getMockMvc()
				.perform(
						delete(getDataController().getFullRequestMapping() + "/" + DeleteAllDataController.REQUEST_MAPPING_DELETE_ALL)
								.content(getObjectMapper().writeValueAsString(expected))
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data").isArray())
				.andDo(print())
				.andDo(log());

		for (Entity entity : expected) {
			assertNull(getBaseService().findById(entity.getId()).getData());
		}

	}

}
