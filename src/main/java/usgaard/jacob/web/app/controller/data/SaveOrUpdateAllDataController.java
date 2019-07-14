package usgaard.jacob.web.app.controller.data;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;

public interface SaveOrUpdateAllDataController<Entity extends BaseEntity> extends DataController<Entity> {

	public static final String REQUEST_METHOD_SAVE_OR_UDPATE_ALL = "saveOrUpdateAll";

	/**
	 * 
	 * @param entities
	 * @return
	 */
	@RequestMapping(
			method = { RequestMethod.PUT, RequestMethod.POST },
			path = { REQUEST_METHOD_SAVE_OR_UDPATE_ALL },
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public default @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> saveOrUpdateAll(@RequestBody Collection<Entity> entities) {
		return getResponseEntity(getBaseService().saveOrUpdateAll(entities));
	}

}
