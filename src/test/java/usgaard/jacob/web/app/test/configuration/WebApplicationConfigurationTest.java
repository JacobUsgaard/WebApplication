package usgaard.jacob.web.app.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import usgaard.jacob.web.app.test.BaseTest;

@Configuration
@ComponentScan(basePackageClasses = { BaseTest.class })
public class WebApplicationConfigurationTest {

}
