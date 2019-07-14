package usgaard.jacob.web.app.test.controller.data;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import usgaard.jacob.web.app.controller.data.FindByIdDataController;
import usgaard.jacob.web.app.entity.BaseEntity;

@Transactional
public interface FindByIdDataControllerTest<Entity extends BaseEntity> extends DataControllerTest<Entity> {

	@Test
	public default void findByIdTest() throws Exception {
		Entity expected = getBaseService().saveOrUpdate(getEntityTest().create()).getData();

		getMockMvc()
				.perform(
						get(getDataController().getFullRequestMapping() + "/" + FindByIdDataController.REQUEST_MAPPING_FIND_BY_ID)
								.param("id", expected.getId().toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.data.id").value(expected.getId()))
				.andDo(print())
				.andDo(log());
	}
}
