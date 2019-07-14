package usgaard.jacob.web.app.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.BaseService;
import usgaard.jacob.web.app.service.response.ServiceResponse;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;
import usgaard.jacob.web.app.test.BaseTest;

/**
 * Basic tests that should be run for every service implementation.
 *
 * @param <Entity>
 */
public abstract class ServiceBaseTest<Entity extends BaseEntity> extends BaseTest<Entity> {

	@Autowired
	protected BaseService<Entity> baseService;

	@Test
	public void deleteTest() {
		Entity entity = entityTest.create();
		final Entity expected = baseService.saveOrUpdate(entity).getData();
		ServiceResponse<Entity> serviceResponse = baseService.findById(expected.getId());
		assertEquals(expected, serviceResponse.getData());
		baseService.delete(expected);
		assertNull(baseService.findById(expected.getId()).getData());
	}

	@Test
	public void deleteAllTest() {
		Collection<Entity> expected = entityTest.createAll(10);
		Collection<Entity> actual = baseService.saveOrUpdateAll(expected).getData();

		baseService.deleteAll(actual);

		for (Entity entity : actual) {
			assertNull(baseService.findById(entity.getId()).getData());
		}

	}

	@Test
	public void findByIdTest() {
		Entity expected = baseService.saveOrUpdate(entityTest.create()).getData();
		entityManager.flush();

		ServiceResponse<Entity> serviceResponse = baseService.findById(expected.getId());
		assertFalse(serviceResponse.hasErrors());
		assertNotNull(serviceResponse.getData());
	}

	@Test
	public void saveOrUpdateTest() {
		ServiceResponse<Entity> serviceResponse = baseService.saveOrUpdate(entityTest.create());
		entityManager.flush();
		assertFalse(serviceResponse.hasErrors());
		assertNotNull(serviceResponse.getData());
	}

	@Test
	public void saveOrUpdateAllTest() {
		ArrayList<Entity> entities = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			entities.add(entityTest.create());
		}
		ServiceResponseCollection<Entity> serviceResponseCollection = baseService.saveOrUpdateAll(entities);
		entityManager.flush();

		assertFalse(serviceResponseCollection.hasErrors());
		assertNotNull(serviceResponseCollection.getData());
		assertEquals(serviceResponseCollection.getData().size(), entities.size());
		for (Entity entity : serviceResponseCollection.getData()) {
			assertNotNull(entity.getId());
		}
	}

}
