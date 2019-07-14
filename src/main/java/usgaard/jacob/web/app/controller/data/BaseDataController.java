package usgaard.jacob.web.app.controller.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usgaard.jacob.web.app.controller.BaseController;
import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.service.BaseService;

/**
 * Base controller with convenience methods for retrieving data.
 * 
 * TODO add more Content Types
 * 
 * @param <Entity>
 */
@RestController
@RequestMapping(BaseDataController.BASE_REQUEST_MAPPING)
public abstract class BaseDataController<Entity extends BaseEntity> extends BaseController implements DataController<Entity> {

	public static final String BASE_REQUEST_MAPPING = "data";

	/**
	 * The base service for the generic type specified during class construction.
	 */
	@Autowired
	protected BaseService<Entity> baseService;

	@Override
	public BaseService<Entity> getBaseService() {
		return baseService;
	}

	public String getFullRequestMapping() {
		return getRequestMapping();
	}

	protected abstract String getRequestMapping();
}