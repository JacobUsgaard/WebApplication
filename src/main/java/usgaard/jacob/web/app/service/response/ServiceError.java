package usgaard.jacob.web.app.service.response;

public class ServiceError {
	public static final String ERROR_MESSAGE_EMPTY = "This field cannot be empty.";

	private String errorMessage;
	private String propertyName;

	public ServiceError(String propertyName, String errorMessage) {
		super();
		this.errorMessage = errorMessage;
		this.propertyName = propertyName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
