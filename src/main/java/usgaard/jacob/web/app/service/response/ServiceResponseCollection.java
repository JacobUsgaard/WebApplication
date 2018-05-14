package usgaard.jacob.web.app.service.response;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import usgaard.jacob.web.app.entity.BaseEntity;

/**
 * 
 * @author Jacob Usgaard
 *
 * @param <Entity>
 */
public class ServiceResponseCollection<Entity extends BaseEntity> {
	protected final Collection<Entity> entities;

	protected final Collection<ServiceError<Entity>> serviceErrors;

	public ServiceResponseCollection(Collection<Entity> entities, Collection<ServiceError<Entity>> serviceErrors) {
		this.entities = new LinkedList<>(entities);

		if (serviceErrors == null) {
			this.serviceErrors = new LinkedList<>();
		} else {
			this.serviceErrors = new LinkedList<>(serviceErrors);
		}
	}

	public ServiceResponseCollection(Collection<Entity> entities) {
		this(entities, (Collection<ServiceError<Entity>>) null);
	}

	public ServiceResponseCollection(Entity entity, ServiceError<Entity> serviceError) {
		this(Arrays.asList(entity), Arrays.asList(serviceError));
	}

	public ServiceResponseCollection(Collection<Entity> entities, ServiceError<Entity> serviceError) {
		this(entities, Arrays.asList(serviceError));
	}

	public ServiceResponseCollection(Entity entity, Collection<ServiceError<Entity>> serviceErrors) {
		this(Arrays.asList(entity), serviceErrors);
	}

	public void addError(ServiceError<Entity> serviceError) {
		serviceErrors.add(serviceError);
	}

	public void addErrors(Collection<ServiceError<Entity>> serviceErrors) {
		this.serviceErrors.addAll(serviceErrors);
	}

	public boolean hasErrors() {
		return !serviceErrors.isEmpty();
	}

	public Collection<Entity> getEntities() {
		return new LinkedList<>(entities);
	}

	public Collection<ServiceError<Entity>> getServiceErrors() {
		return new LinkedList<>(serviceErrors);
	}
}
