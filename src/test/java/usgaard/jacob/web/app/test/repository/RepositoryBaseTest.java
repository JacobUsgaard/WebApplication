package usgaard.jacob.web.app.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.repository.BaseRepository;
import usgaard.jacob.web.app.test.BaseTest;

/**
 * Basic tests that should be run for every repository implementation.
 *
 * @param <Entity>
 */
public abstract class RepositoryBaseTest<Entity extends BaseEntity> extends BaseTest<Entity> {

	@Autowired
	protected BaseRepository<Entity> baseRepository;

	@Test
	public void deleteTest() {
		Entity expected = baseRepository.saveOrUpdate(entityTest.create());
		entityManager.flush();
		assertNotNull(expected);
		assertNotNull(baseRepository.findById(expected.getId()));
		baseRepository.delete(expected);
		assertNull(baseRepository.findById(expected.getId()));
	}

	@Test
	public void deleteAllTest() {
		Collection<Entity> expected = entityTest.createAll(10);
		Collection<Entity> actual = baseRepository.saveOrUpdateAll(expected);

		baseRepository.deleteAll(actual);

		for (Entity entity : actual) {
			assertNull(baseRepository.findById(entity.getId()));
		}

	}

	@Test
	public void findByIdTest() {
		Entity expected = baseRepository.saveOrUpdate(entityTest.create());
		entityManager.flush();

		Entity actual = baseRepository.findById(expected.getId());
		assertNotNull(actual);
	}

	@Test
	public void saveOrUpdateTest() {
		Entity expected = entityTest.create();
		Entity actual = baseRepository.saveOrUpdate(expected);
		entityManager.flush();
		assertNotNull(actual.getId());
	}

	@Test
	public void saveOrUpdateAllTest() {
		Collection<Entity> expected = entityTest.createAll(10);
		Collection<Entity> actual = baseRepository.saveOrUpdateAll(expected);
		entityManager.flush();

		assertEquals(expected.size(), actual.size());
		for (Entity entity : actual) {
			assertNotNull(entity.getId());
		}
	}
}
