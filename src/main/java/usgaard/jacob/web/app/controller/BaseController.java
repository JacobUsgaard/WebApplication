package usgaard.jacob.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

	/**
	 * The logger created for each implementation class.
	 */
	protected final Logger logger;

	/**
	 * 
	 */
	public BaseController() {
		logger = LoggerFactory.getLogger(getClass());
	}

}
