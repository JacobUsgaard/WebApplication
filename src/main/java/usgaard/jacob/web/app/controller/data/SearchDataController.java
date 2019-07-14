package usgaard.jacob.web.app.controller.data;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;

public interface SearchDataController<Entity extends BaseEntity> extends DataController<Entity> {

	
	/**
	 * 
	 * @param httpServletRequest
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping(
			path = { "search" }, 
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }
	)
	public default @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> search(HttpServletRequest httpServletRequest)throws UnsupportedEncodingException {
		return getResponseEntity(getBaseService().search(httpServletRequest.getQueryString()));
	}
}
