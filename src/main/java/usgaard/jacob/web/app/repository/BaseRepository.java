package usgaard.jacob.web.app.repository;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import usgaard.jacob.web.app.configuration.WebApplicationConfiguration;
import usgaard.jacob.web.app.entity.BaseEntity;

/**
 * Base repository with common methods.
 * 
 * @author Jacob Usgaard
 *
 * @param <Entity>
 */
@Repository
public abstract class BaseRepository<Entity extends BaseEntity> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The logger created for each implementation class.
	 */
	protected final Logger logger;

	/**
	 * The generic type specified with this class.
	 */
	protected final Class<Entity> entityClass;

	/**
	 * The entity manager housing all entities.
	 * 
	 * @see WebApplicationConfiguration#localContainerEntityManagerFactoryBean()
	 */
	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * Creates BaseRepository.
	 * 
	 * Instantiates logger for Entity and resolves generic type specified.
	 * 
	 * @throws UnsupportedOperationException
	 *             If the generic type is not specified.
	 */
	@SuppressWarnings("unchecked")
	public BaseRepository() {
		logger = LoggerFactory.getLogger(getClass());

		entityClass = (Class<Entity>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepository.class);
		if (entityClass == null) {
			throw new UnsupportedOperationException(
					"Generic type cannot be null for BaseRepository implementation: " + getClass());
		}
	}

	/**
	 * Remove entity from {@link DataSource} using {@link EntityManager}.
	 * 
	 * @param entity
	 *            The entity to be deleted.
	 */
	public void delete(@NotNull Entity entity) {
		if (entity == null) {
			return;
		}

		logger.debug("deleting entity: {}", entity.toString());
		if (entityManager.contains(entity)) {
			entityManager.merge(entity);
		}

		entityManager.remove(entity);
	}

	/**
	 * Convenience method to delete multiple entities.
	 * 
	 * @param entities
	 *            The entities to be deleted.
	 */
	public void deleteAll(@NotNull Collection<Entity> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}

		for (Entity entity : entities) {
			delete(entity);
		}
	}

	/**
	 * Find all entities of this type.
	 * 
	 * @return The collection of entities found.
	 */
	public Collection<Entity> findAll() {
		logger.debug("finding all entities");
		CriteriaQuery<Entity> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
		criteriaQuery.select(criteriaQuery.from(entityClass));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	/**
	 * Find a single entity by its identifier.
	 * 
	 * @param id
	 *            The id of the entity to be found.
	 * @return The entity, null if it doesn't exist.
	 * 
	 * @see Id
	 */
	public Entity findById(@NotNull Object id) {
		logger.debug("find entity by id: {}", id.toString());
		return entityManager.find(entityClass, id);
	}

	/**
	 * Synchronizes entity with {@link DataSource} using {@link EntityManager}.
	 * 
	 * @param entity
	 *            The entity to be synchronized.
	 */
	public void saveOrUpdate(@NotNull Entity entity) {
		if (entity == null) {
			return;
		}

		logger.debug("saving entity: {}", entity.toString());
		entityManager.merge(entity);
	}

	/**
	 * Convenience method for synchronizing multiple entities.
	 * 
	 * @param entities
	 *            The entities to be synchronized.
	 * 
	 * @see #saveOrUpdate(BaseEntity)
	 */
	public void saveOrUpdateAll(@NotNull Collection<Entity> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}

		for (Entity entity : entities) {
			saveOrUpdate(entity);
		}
	}
}