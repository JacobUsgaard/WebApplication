package usgaard.jacob.web.app.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import usgaard.jacob.web.app.configuration.WebApplicationConfiguration;
import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.test.configuration.WebApplicationConfigurationTest;
import usgaard.jacob.web.app.test.entity.EntityTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebApplicationConfiguration.class, WebApplicationConfigurationTest.class })
@Transactional
@WebAppConfiguration
public abstract class BaseTest<Entity extends BaseEntity> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected EntityTest<Entity> entityTest;

	protected final Logger logger;

	protected final Class<Entity> entityClass;

	@SuppressWarnings("unchecked")
	public BaseTest() {
		logger = LoggerFactory.getLogger(getClass());

		entityClass = (Class<Entity>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseTest.class);
		if (entityClass == null) {
			throw new UnsupportedOperationException(String.format("Generic type cannot be null for %s implementation: %s", BaseTest.class, getClass()));
		}
	}

	public Class<Entity> getEntityClass() {
		return entityClass;
	}
}
