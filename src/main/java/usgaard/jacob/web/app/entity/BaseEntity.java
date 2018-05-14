package usgaard.jacob.web.app.entity;

import java.io.Serializable;

import usgaard.jacob.web.app.controller.data.BaseDataController;
import usgaard.jacob.web.app.repository.BaseRepository;
import usgaard.jacob.web.app.service.BaseService;

/**
 * Base entity. Entities wishing to use {@link BaseDataController},
 * {@link BaseService}, and {@link BaseRepository} must extend this class.
 * 
 * @author Jacob Usgaard
 *
 */
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
}
