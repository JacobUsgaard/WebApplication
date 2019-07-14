package usgaard.jacob.web.app.repository;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;

import usgaard.jacob.web.app.configuration.WebApplicationConfiguration;
import usgaard.jacob.web.app.entity.BaseEntity;

/**
 * Base repository with common methods.
 * 
 * @param <Entity>
 */
@Repository
public abstract class BaseRepository<Entity extends BaseEntity> {

	protected enum Operator {
		EQUALS("\\="),
		GREATER_THAN("\\>"),
		GREATER_THAN_OR_EQUAL("\\>\\="),
		LESS_THAN("\\<"),
		LESS_THAN_OR_EQUAL("\\<\\="),
		LIKE("\\=\\*");

		private Pattern pattern;

		private Operator(String regex) {
			pattern = Pattern.compile(regex);
		}
	}

	/**
	 * The logger created for each implementation class.
	 */
	protected final Logger logger;

	/**
	 * The generic type specified with this class.
	 */
	protected final Class<Entity> entityClass;

	/**
	 * The entity manager housing all entities.
	 * 
	 * @see WebApplicationConfiguration#localContainerEntityManagerFactoryBean()
	 */
	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * 
	 */
	protected final BeanInfo beanInfo;

	protected final LinkedList<Field> entityFields;

	/**
	 * Creates BaseRepository.
	 * 
	 * Instantiates logger for Entity and resolves generic type
	 * specified.currentLength
	 * 
	 * @throws UnsupportedOperationException If the generic type is not specified.
	 */
	@SuppressWarnings("unchecked")
	public BaseRepository() {
		logger = LoggerFactory.getLogger(getClass());

		entityClass = (Class<Entity>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepository.class);
		if (entityClass == null) {
			throw new UnsupportedOperationException("Generic type cannot be null for BaseRepository implementation: " + getClass());
		}

		try {
			beanInfo = Introspector.getBeanInfo(entityClass);
		} catch (IntrospectionException introspectionException) {
			throw new BeanCreationException(getClass().getSimpleName(), "Unable to get Bean Info",
					introspectionException);
		}

		entityFields = new LinkedList<>();
		for (Class<?> c = entityClass; c != null && c.equals(Object.class); c = c.getSuperclass()) {
			entityFields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
	}

	/**
	 * Remove entity from {@link DataSource} using {@link EntityManager}.
	 * 
	 * @param entity The entity to be deleted.
	 */
	public void delete(@NotNull Entity entity) {
		if (entity == null) {
			return;
		}

		logger.debug("deleting entity: {}", entity);
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	/**
	 * Convenience method to delete multiple entities.
	 * 
	 * @param entities The entities to be deleted.
	 */
	public void deleteAll(@NotNull Collection<Entity> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}

		for (Entity entity : entities) {
			delete(entity);
		}
	}

	/**
	 * Find a single entity by its identifier.
	 * 
	 * @param id The id of the entity to be found.
	 * @return The entity, null if it doesn't exist.
	 * 
	 * @see Id
	 */
	public Entity findById(@NotNull Object id) {
		logger.info("find entity by id: {}", id);
		return entityManager.find(entityClass, id);
	}

	/**
	 * Synchronizes entity with {@link DataSource} using {@link EntityManager}.
	 * 
	 * @param entity The entity to be synchronized.
	 */
	public Entity saveOrUpdate(@NotNull Entity entity) {
		logger.debug("saving entity: {}", entity);
		return entityManager.merge(entity);
	}

	/**
	 * Convenience method for synchronizing multiple entities.
	 * 
	 * @param entities The entities to be synchronized.
	 * 
	 * @see #saveOrUpdate(BaseEntity)
	 */
	public Collection<Entity> saveOrUpdateAll(@NotNull Collection<Entity> entities) {
		ArrayList<Entity> list = new ArrayList<>(entities == null ? 0 : entities.size());
		if (entities == null || entities.isEmpty()) {
			return list;
		}

		for (Entity entity : entities) {
			list.add(saveOrUpdate(entity));
		}

		return list;
	}

	public List<Entity> search(String[] columns, Object[] values) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<Entity> root = criteriaQuery.from(entityClass);
		if (columns == null || values == null || columns.length != columns.length) {
			return entityManager.createQuery(criteriaQuery.select(root)).getResultList();
		}

		ArrayList<Predicate> predicates = new ArrayList<>(columns.length);

		columnLoop: for (int i = 0; i < columns.length; i++) {
			String columnName = columns[i];

			for (Field field : entityFields) {
				Column column = field.getAnnotation(Column.class);
				if (column == null) {
					continue;
				}

				if (column.name().equalsIgnoreCase(columnName)) {
					predicates.add(criteriaBuilder.equal(root.get(field.getName()), values[i]));
					continue columnLoop;
				}
			}

			throw new UnsupportedOperationException("Unable to match column name: " + columnName
					+ " to any @Column field in Entity: " + entityClass.getName());
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	public List<Entity> search(final String query) throws UnsupportedEncodingException {
		logger.info("query: {}", query);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<Entity> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);

		if (query != null) {
			String decodedQuery = URLDecoder.decode(query, StandardCharsets.UTF_8.name());
			logger.info("decoded query: {}", decodedQuery);
			String[] nameValuePairs = decodedQuery.split("\\&", -1);
			logger.info("name value pairs: {}", nameValuePairs.length);

			for (String nameValuePair : nameValuePairs) {
				logger.info("NameValuePair: {}", nameValuePair);

				int maxLength = -1, start = -1, end = -1;
				Operator operatorMatch = null;
				for (Operator operator : Operator.values()) {
					Matcher matcher = operator.pattern.matcher(nameValuePair);

					if (!matcher.find()) {
						logger.info("operator {} does not match input string: {}", operator.name(), nameValuePair);
						continue;
					}

					logger.info("operator {} matches NameValuePair", operator.name());
					start = matcher.start();
					end = matcher.end();
					int currentLength = end - start;
					if (maxLength < currentLength) {
						maxLength = currentLength;
						operatorMatch = operator;
					}
				}

				if (operatorMatch == null) {
					throw new UnsupportedOperationException("Unable to find operator for parameter: " + nameValuePair);
				}

				String name = nameValuePair.substring(0, start);
				String value = nameValuePair.substring(end);

				logger.info("parameter mapped: {}, value: {}, operator: {}", name, value, operatorMatch.name());

				if (name.equalsIgnoreCase("order")) {
					String[] orders = value.split(",");
					logger.info("orders: {}", orders.length);
					ArrayList<Order> orderList = new ArrayList<>(orders.length);
					for (String order : orders) {
						logger.info("order: {}", order);
						String attributeName = order.substring(0, order.length() - 1);
						if (order.endsWith("+")) {
							orderList.add(criteriaBuilder.asc(root.get(attributeName)));
						} else if (order.endsWith("-")) {
							orderList.add(criteriaBuilder.desc(root.get(attributeName)));
						} else {
							throw new UnsupportedOperationException("Invalid order operation: " + order);
						}
					}

					criteriaQuery.orderBy(orderList);
					continue;
				}

				for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
					String propertyName = propertyDescriptor.getName();
					logger.info("checking property: {}", propertyName);

					if (propertyName.equalsIgnoreCase(name)) {

						Path<Object> path = root.get(propertyName);
						Predicate predicate;
						switch (operatorMatch) {
						case EQUALS:
							predicate = criteriaBuilder.equal(path, value);
							break;

						case GREATER_THAN:
							predicate = criteriaBuilder.greaterThan(path.as(String.class), value);
							break;

						case GREATER_THAN_OR_EQUAL:
							predicate = criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), value);
							break;

						case LIKE:
							predicate = criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)),
									"%" + value.toLowerCase() + "%");
							break;

						case LESS_THAN:
							predicate = criteriaBuilder.lessThan(path.as(String.class), value);
							break;

						case LESS_THAN_OR_EQUAL:
							predicate = criteriaBuilder.lessThanOrEqualTo(path.as(String.class), value);
							break;

						default:
							throw new UnsupportedOperationException(
									"Invalid operation mapping: " + operatorMatch.name());
						}

						criteriaQuery.where(predicate);
					}
				}
			}
		}

		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}