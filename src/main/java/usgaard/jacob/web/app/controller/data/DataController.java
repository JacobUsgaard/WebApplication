/**
 * 
 */
package usgaard.jacob.web.app.controller.data;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.BaseService;
import usgaard.jacob.web.app.service.response.ServiceResponse;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;

/**
 *
 */
public interface DataController<Entity extends BaseEntity> {

	/**
	 * 
	 * @param serviceResponse
	 * @return
	 */
	public default ResponseEntity<ServiceResponse<Entity>> getResponseEntity(ServiceResponse<Entity> serviceResponse) {

		BodyBuilder bodyBuilder;

		if (serviceResponse.hasErrors()) {
			bodyBuilder = ResponseEntity.badRequest();
		} else {
			bodyBuilder = ResponseEntity.ok();
		}

		return bodyBuilder.body(serviceResponse);
	}

	/**
	 * 
	 * @param serviceResponseCollection
	 * @return
	 */
	public default ResponseEntity<ServiceResponseCollection<Entity>> getResponseEntity(
			ServiceResponseCollection<Entity> serviceResponseCollection) {

		BodyBuilder bodyBuilder;

		if (serviceResponseCollection.hasErrors()) {
			bodyBuilder = ResponseEntity.badRequest();
		} else {
			bodyBuilder = ResponseEntity.ok();
		}

		return bodyBuilder.body(serviceResponseCollection);
	}
	
	public BaseService<Entity> getBaseService();
	
}
