package usgaard.jacob.web.app.controller.data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.BaseService;
import usgaard.jacob.web.app.service.response.ServiceResponse;
import usgaard.jacob.web.app.service.response.ServiceResponseCollection;

/**
 * Base controller with convenience methods for retrieving data.
 * 
 * TODO add more Content Types
 * 
 * @author Jacob Usgaard
 *
 * @param <Entity>
 */
@RestController
public abstract class BaseDataController<Entity extends BaseEntity> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The logger created for each implementation class.
	 */
	protected final Logger logger;

	/**
	 * The generic type specified with this class.
	 */
	protected final Class<Entity> entityClass;

	/**
	 * The base service for the generic type specified during class construction.
	 */
	@Inject
	protected BaseService<Entity> baseService;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public BaseDataController() {
		logger = LoggerFactory.getLogger(getClass());

		this.entityClass = (Class<Entity>) GenericTypeResolver
				.resolveTypeArgument(getClass(), BaseDataController.class);
		if (this.entityClass == null) {
			throw new BeanCreationException(
					"Generic type cannot be null for BaseRepository implementation: " + getClass());
		}
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.DELETE }, path = { "/delete" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponse<Entity>> delete(@RequestBody Entity entity) {
		return getResponseEntity(baseService.delete(entity));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.DELETE }, path = { "/{id}" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponse<Entity>> deleteById(@PathVariable BigInteger id) {
		return getResponseEntity(baseService.delete(id));
	}

	/**
	 * 
	 * @param entities
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.DELETE }, path = { "/deleteAll" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> deleteAll(
			@RequestBody Collection<Entity> entities) {
		return getResponseEntity(baseService.deleteAll(entities));
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET }, path = { "/findAll" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> findAll() {
		return getResponseEntity(baseService.findAll());
	}

	/**
	 * Finds an entity by a given id.
	 * 
	 * TODO add other ways of finding e.g. path variable
	 * 
	 * @param id
	 *            The id of the entity.
	 * @return The entity with the given id in the requested media type.
	 */
	@RequestMapping(method = { RequestMethod.GET }, path = { "/findById" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponse<Entity>> findById(@RequestParam BigInteger id) {
		return getResponseEntity(baseService.findById(id));
	}

	/**
	 * 
	 * @param serviceResponse
	 * @return
	 */
	protected ResponseEntity<ServiceResponse<Entity>> getResponseEntity(ServiceResponse<Entity> serviceResponse) {

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
	protected ResponseEntity<ServiceResponseCollection<Entity>> getResponseEntity(
			ServiceResponseCollection<Entity> serviceResponseCollection) {

		BodyBuilder bodyBuilder;

		if (serviceResponseCollection.hasErrors()) {
			bodyBuilder = ResponseEntity.badRequest();
		} else {
			bodyBuilder = ResponseEntity.ok();
		}

		return bodyBuilder.body(serviceResponseCollection);
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST }, path = { "/saveOrUpdate" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponse<Entity>> saveOrUpdate(@RequestBody Entity entity) {
		return getResponseEntity(baseService.saveOrUpdate(entity));
	}

	/**
	 * 
	 * @param entities
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST }, path = { "/saveOrUpdateAll" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> saveOrUpdateAll(
			@RequestBody Collection<Entity> entities) {
		return getResponseEntity(baseService.saveOrUpdateAll(entities));
	}

	/**
	 * 
	 * @param httpServletRequest
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(method = { RequestMethod.GET }, path = { "search" }, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<ServiceResponseCollection<Entity>> search(HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		return getResponseEntity(baseService.search(httpServletRequest.getQueryString()));
	}
}