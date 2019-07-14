package usgaard.jacob.web.app.controller.data;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.response.ServiceResponse;

/**
 * 
 * @param <Entity>
 */
public interface DeleteDataController<Entity extends BaseEntity> extends DataController<Entity> {

	public static final String REQUEST_MAPPING_DELETE = "delete";
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	@DeleteMapping(
			path = { REQUEST_MAPPING_DELETE }, 
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public default @ResponseBody ResponseEntity<ServiceResponse<Entity>> delete(@RequestBody Entity entity) {
		return getResponseEntity(getBaseService().delete(entity));
	}
	
}
