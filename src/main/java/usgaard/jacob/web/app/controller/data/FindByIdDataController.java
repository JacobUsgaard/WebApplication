package usgaard.jacob.web.app.controller.data;

import java.math.BigInteger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.response.ServiceResponse;

public interface FindByIdDataController<Entity extends BaseEntity> extends DataController<Entity>{
	
	public static final String REQUEST_MAPPING_FIND_BY_ID = "/findById";
	
	/**
	 * Finds an entity by a given id.
	 * 
	 * TODO add other ways of finding e.g. path variable
	 * 
	 * @param id
	 *            The id of the entity.
	 * @return The entity with the given id in the requested media type.
	 */
	@GetMapping(
			path = { REQUEST_MAPPING_FIND_BY_ID },
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }
	)
	public default @ResponseBody ResponseEntity<ServiceResponse<Entity>> findById(@RequestParam BigInteger id) {
		return getResponseEntity(getBaseService().findById(id));
	}
}
