package usgaard.jacob.web.app.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;

import javax.inject.Inject;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.repository.BaseRepository;
import usgaard.jacob.web.app.service.response.ServiceError;
import usgaard.jacob.web.app.service.response.ServiceResponse;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;

/**
 * Base service for business validation.
 * 
 * @author Jacob Usgaard
 *
 * @param <Entity>
 */
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Service
public abstract class BaseService<Entity extends BaseEntity> implements Serializable {

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
	 * The repository for the generic type of entity specified at class
	 * construction.
	 */
	@Inject
	protected BaseRepository<Entity> baseRepository;

	/**
	 * Creates BaseService.
	 * 
	 * Instantiates logger for Entity and resolves Class.
	 */
	@SuppressWarnings("unchecked")
	public BaseService() {
		logger = LoggerFactory.getLogger(getClass());

		entityClass = (Class<Entity>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseService.class);
		if (entityClass == null) {
			throw new BeanCreationException(
					"Generic type cannot be null for BaseRepository implementation: " + getClass());
		}
	}

	/**
	 * Delete the specified entity.
	 * 
	 * @param entity
	 *            The entity to be deleted.
	 * @return The response containing the entity and any errors that occurred.
	 */
	public ServiceResponse<Entity> delete(@NotNull Entity entity) {
		baseRepository.delete(entity);
		return new ServiceResponse<>(entity);
	}

	/**
	 * Convenience method to delete entity by id.
	 * 
	 * @param id
	 *            The id of the entity to be deleted.
	 * @return The response containing the entity and any errors that occurred.
	 */
	public ServiceResponse<Entity> delete(@NotNull Object id) {
		ServiceResponse<Entity> serviceResponse = findById(id);
		if (serviceResponse.hasErrors()) {
			return serviceResponse;
		}

		return delete(serviceResponse.getEntity());
	}

	/**
	 * Convenience method for deleting multiple entities.
	 * 
	 * @param entities
	 *            The entities to be deleted.
	 * @return The response containing the entities and any errors that occurred.
	 */
	public ServiceResponseCollection<Entity> deleteAll(@NotNull Collection<Entity> entities) {
		baseRepository.deleteAll(entities);
		return new ServiceResponseCollection<>(entities);
	}

	/**
	 * Find all entities of this type.
	 * 
	 * @return The collection of entities found.
	 */
	public ServiceResponseCollection<Entity> findAll() {
		return new ServiceResponseCollection<>(baseRepository.findAll());
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
	public ServiceResponse<Entity> findById(@NotNull Object id) {
		return new ServiceResponse<>(baseRepository.findById(id));
	}

	/**
	 * Validates entity and calls {@link BaseRepository#saveOrUpdate(BaseEntity)}.
	 * 
	 * @param entity
	 *            The entity to be synchronized.
	 * @return The response containing the entity and any errors that occurred
	 *         during validation.
	 * 
	 * @see #validate(BaseEntity)
	 * @see BaseRepository#saveOrUpdate(BaseEntity)
	 */
	public ServiceResponse<Entity> saveOrUpdate(@NotNull Entity entity) {
		ServiceResponse<Entity> serviceResponse = validate(entity);

		if (serviceResponse.hasErrors()) {
			return serviceResponse;
		}

		baseRepository.saveOrUpdate(entity);

		return new ServiceResponse<>(entity);
	}

	/**
	 * Convenience method for synchronizing multiple entities
	 * 
	 * @param entities
	 *            The entities to be synchronized.
	 * @return The response containing the entities and any errors that occurred
	 *         during validation.
	 * 
	 * @see #validate(Collection)
	 * @see BaseRepository#saveOrUpdateAll(Collection)
	 */
	public ServiceResponseCollection<Entity> saveOrUpdateAll(@NotNull Collection<Entity> entities) {
		ServiceResponseCollection<Entity> serviceResponseCollection = validate(entities);

		if (serviceResponseCollection.hasErrors()) {
			return serviceResponseCollection;
		}

		baseRepository.saveOrUpdateAll(entities);

		return new ServiceResponseCollection<>(entities);
	}

	/**
	 * Convenience method to validate multiple entities.
	 * 
	 * @param entities
	 *            The entities to be validated.
	 * @return The response containing the entities and any errors that occurred
	 *         during validation.
	 */
	public final ServiceResponseCollection<Entity> validate(@NotNull Collection<Entity> entities) {
		LinkedList<ServiceError<Entity>> serviceErrors = new LinkedList<>();
		for (Entity entity : entities) {
			ServiceResponse<Entity> serviceResponse = validate(entity);

			if (serviceResponse.hasErrors()) {
				serviceErrors.addAll(serviceResponse.getServiceErrors());
			}
		}

		return new ServiceResponseCollection<>(entities, serviceErrors);
	}

	/**
	 * Validate the entity using business rules.
	 * 
	 * @param entity
	 *            The entity to be validated.
	 * @return The response containing the entity and any errors that occurred
	 *         during validation.
	 */
	public abstract ServiceResponse<Entity> validate(@NotNull Entity entity);

	/**
	 * 
	 * @param query
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ServiceResponseCollection<Entity> search(String query) throws UnsupportedEncodingException {
		return new ServiceResponseCollection<>(baseRepository.search(query));
	}

}