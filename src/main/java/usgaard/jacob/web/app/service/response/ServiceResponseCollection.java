package usgaard.jacob.web.app.service.response;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author Jacob Usgaard
 *
 * @param <T>
 */
public class ServiceResponseCollection<T> extends ServiceResponse<Collection<T>> {
	protected final Collection<T> entities;

	protected final Collection<ServiceError> serviceErrors;

	public ServiceResponseCollection(Collection<T> entities, Collection<ServiceError> serviceErrors) {
		super(entities);
		this.entities = new LinkedList<>(entities);

		if (serviceErrors == null) {
			this.serviceErrors = new LinkedList<>();
		} else {
			this.serviceErrors = new LinkedList<>(serviceErrors);
		}
	}

	public ServiceResponseCollection(Collection<T> entities) {
		this(entities, (Collection<ServiceError>) null);
	}

	public ServiceResponseCollection(T T, ServiceError serviceError) {
		this(Arrays.asList(T), Arrays.asList(serviceError));
	}

	public ServiceResponseCollection(Collection<T> entities, ServiceError serviceError) {
		this(entities, Arrays.asList(serviceError));
	}

	public ServiceResponseCollection(T T, Collection<ServiceError> serviceErrors) {
		this(Arrays.asList(T), serviceErrors);
	}

	public void addError(ServiceError serviceError) {
		serviceErrors.add(serviceError);
	}

	public void addErrors(Collection<ServiceError> serviceErrors) {
		this.serviceErrors.addAll(serviceErrors);
	}

	public boolean hasErrors() {
		return !serviceErrors.isEmpty();
	}

	public Collection<T> getEntities() {
		return new LinkedList<>(entities);
	}

	public Collection<ServiceError> getServiceErrors() {
		return new LinkedList<>(serviceErrors);
	}
}
