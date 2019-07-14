package usgaard.jacob.web.app.controller.data;

import java.math.BigInteger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.response.ServiceResponse;

public interface DeleteByIdDataController<Entity extends BaseEntity> extends DataController<Entity> {

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(
			path = { "/{id}" }, 
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }
		)
	public default @ResponseBody ResponseEntity<ServiceResponse<Entity>> deleteById(@PathVariable BigInteger id) {
		return getResponseEntity(getBaseService().deleteById(id));
	}
}
