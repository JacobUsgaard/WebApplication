package usgaard.jacob.web.app.service.response;

import java.util.Collection;
import java.util.LinkedList;

import usgaard.jacob.web.app.entity.BaseEntity;

/**
 * 
 * @author Jacob Usgaard
 *
 * @param <Entity>
 */
public class ServiceResponse<Entity extends BaseEntity> {
	protected final Entity entity;

	protected final Collection<ServiceError<Entity>> serviceErrors;

	public ServiceResponse(Entity entity, Collection<ServiceError<Entity>> serviceErrors) {
		this.entity = entity;

		if (serviceErrors == null) {
			this.serviceErrors = new LinkedList<>();
		} else {
			this.serviceErrors = new LinkedList<>(serviceErrors);
		}
	}

	public ServiceResponse(Entity entity) {
		this(entity, null);
	}

	public void addServiceError(String propertyName, String errorMessage) {
		serviceErrors.add(new ServiceError<>(propertyName, errorMessage));
	}

	public void addError(ServiceError<Entity> serviceError) {
		this.serviceErrors.add(serviceError);
	}

	public void addErrors(Collection<ServiceError<Entity>> serviceErrors) {
		this.serviceErrors.addAll(serviceErrors);
	}

	public boolean hasErrors() {
		return !serviceErrors.isEmpty();
	}

	public Entity getEntity() {
		return entity;
	}

	public Collection<ServiceError<Entity>> getServiceErrors() {
		return new LinkedList<>(serviceErrors);
	}
}
