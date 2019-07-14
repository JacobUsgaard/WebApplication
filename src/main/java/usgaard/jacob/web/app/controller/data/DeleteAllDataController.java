package usgaard.jacob.web.app.controller.data;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;

/**
 * 
 * @param <Entity>
 */
public interface DeleteAllDataController<Entity extends BaseEntity> extends DataController<Entity> {

	public static final String REQUEST_MAPPING_DELETE_ALL = "deleteAll";
	
	/**
	 * 
	 * @param entities
	 * @return
	 */
	@DeleteMapping(
			path = { REQUEST_MAPPING_DELETE_ALL }, 
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }
		)
	public default @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> deleteAll(@RequestBody Collection<Entity> entities) {
		return getResponseEntity(getBaseService().deleteAll(entities));
	}
	
}
