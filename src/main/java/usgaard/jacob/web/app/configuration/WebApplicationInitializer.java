package usgaard.jacob.web.app.configuration;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.HttpConstraintElement;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import usgaard.jacob.web.app.filter.BaseFilter;

public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebApplicationConfiguration.class);

		servletContext.addListener(new ContextLoaderListener(rootContext));

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));

		dispatcher.addMapping("/app/*");

		dispatcher.setServletSecurity(new ServletSecurityElement(new HttpConstraintElement(TransportGuarantee.NONE)));

		dispatcher.setAsyncSupported(true);

		ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider(false);
		classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(Component.class));

		for (BeanDefinition beanDefinition : classPathScanningCandidateComponentProvider.findCandidateComponents(BaseFilter.class.getPackage().getName())) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends BaseFilter> filterClass = (Class<? extends BaseFilter>) Class.forName(beanDefinition.getBeanClassName());

				if (Modifier.isAbstract(filterClass.getModifiers()) || Modifier.isInterface(filterClass.getModifiers()) || !filterClass.isAssignableFrom(BaseFilter.class)) {
					continue;
				}

				FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(filterClass.getSimpleName(), filterClass);
				filterRegistration.addMappingForServletNames(EnumSet.copyOf(Arrays.asList(DispatcherType.values())), false, "dispatcher");

				LOGGER.info("Adding filter {} to servlet {}", filterClass.getSimpleName(), "dispatcher");

			} catch (ClassNotFoundException classNotFoundException) {
				String message = "Failed to create Filter for class: " + beanDefinition.getBeanClassName();
				LOGGER.error(message, classNotFoundException);
				throw new ServletException(message, classNotFoundException);
			}
		}

		String tmpDirectory = System.getProperty("java.io.tmp");
		if (tmpDirectory == null || tmpDirectory.isEmpty()) {
			tmpDirectory = "/tmp";
		}
		LOGGER.info("Temporary directory for multipart file uploads: {}", tmpDirectory);
		dispatcher.setMultipartConfig(new MultipartConfigElement(tmpDirectory, 100000l, 100000l, 10000));
	}

}
