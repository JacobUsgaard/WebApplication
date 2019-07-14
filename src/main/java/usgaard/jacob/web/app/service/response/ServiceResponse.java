package usgaard.jacob.web.app.service.response;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @param <T>
 */
public class ServiceResponse<T> {
	protected final T data;

	protected final Collection<ServiceError> serviceErrors;

	public ServiceResponse(T data, Collection<ServiceError> serviceErrors) {
		this.data = data;

		if (serviceErrors == null) {
			this.serviceErrors = new LinkedList<>();
		} else {
			this.serviceErrors = new LinkedList<>(serviceErrors);
		}
	}

	public ServiceResponse(T data) {
		this(data, null);
	}
	
	public ServiceResponse() {
		this(null);
	}

	public void addServiceError(String propertyName, String errorMessage) {
		serviceErrors.add(new ServiceError(propertyName, errorMessage));
	}

	public void addError(ServiceError serviceError) {
		this.serviceErrors.add(serviceError);
	}

	public void addErrors(Collection<ServiceError> serviceErrors) {
		this.serviceErrors.addAll(serviceErrors);
	}

	public boolean hasErrors() {
		return !serviceErrors.isEmpty();
	}

	public T getData() {
		return data;
	}

	public Collection<ServiceError> getServiceErrors() {
		return new LinkedList<>(serviceErrors);
	}
}
