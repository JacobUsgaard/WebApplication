package usgaard.jacob.web.app.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import oracle.jdbc.driver.OracleDriver;
import usgaard.jacob.web.app.controller.BaseController;
import usgaard.jacob.web.app.controller.data.BaseDataController;
import usgaard.jacob.web.app.entity.BaseEntity;
import usgaard.jacob.web.app.filter.BaseFilter;
import usgaard.jacob.web.app.repository.BaseRepository;
import usgaard.jacob.web.app.service.BaseService;

/**
 * Main configuration class.
 * 
 * 
 * @author Jacob Usgaard
 */

@ComponentScan(basePackageClasses = { BaseController.class, BaseDataController.class, BaseEntity.class,
		BaseFilter.class, BaseRepository.class, BaseService.class })
@Configuration
@ControllerAdvice(basePackageClasses = { BaseController.class, BaseDataController.class })
@EnableTransactionManagement
@EnableWebMvc
public class WebApplicationConfiguration {

	@Autowired
	private DataSource dataSource;

	/**
	 * Creates the data source connecting to the oracle database.
	 * 
	 * @return The data source.
	 */
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(OracleDriver.class.getName());
		basicDataSource.setUrl("jdbc:oracle:thin:@192.168.1.28:1521:orcl");
		basicDataSource.setUsername("event_tracker");
		basicDataSource.setPassword("Goose4523");
		return basicDataSource;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(false);

		localContainerEntityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		localContainerEntityManagerFactoryBean.setPackagesToScan(BaseEntity.class.getPackage().getName());
		localContainerEntityManagerFactoryBean.setDataSource(dataSource);
		return localContainerEntityManagerFactoryBean;
	}

	/**
	 * The transaction manager for the
	 * {@link LocalContainerEntityManagerFactoryBean}.
	 * 
	 * @return The transaction manager.
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(localContainerEntityManagerFactoryBean().getObject());
	}
}
