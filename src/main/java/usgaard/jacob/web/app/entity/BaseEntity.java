package usgaard.jacob.web.app.entity;

import usgaard.jacob.web.app.controller.data.BaseDataController;
import usgaard.jacob.web.app.repository.BaseRepository;
import usgaard.jacob.web.app.service.BaseService;

/**
 * Base entity. Entities wishing to use {@link BaseDataController},
 * {@link BaseService}, and {@link BaseRepository} must extend this class.
 */
public abstract class BaseEntity {

	public abstract Object getId();

}
