package br.com.mapinfo.authservice;

import br.com.mapinfo.authservice.multitenant.*;
import br.com.mapinfo.authservice.multitenant.web.MultiTenantFilter;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AuthServiceApplication {

	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl dsProvider;

	@Autowired
	TenantIdentifierResolver tenantResolver;

	@Autowired
	AutowireCapableBeanFactory beanFactory;

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties sqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return sqlDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	@PersistenceContext
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {

		LocalContainerEntityManagerFactoryBean result = builder.dataSource(dataSource())
				.persistenceUnit(MultiTenantConstants.TENANT_KEY)
				.packages("br.com.mapinfo").build();

		Map<String, Object> props = new HashMap<>();

		props.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DISCRIMINATOR.name());
		props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, dsProvider);
		props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantResolver);

		result.setJpaPropertyMap(props);
		result.setJpaVendorAdapter(jpaVendorAdapter());

		return result;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		// Generate DDL is not supported in Hibernate to multi-tenancy features
		// https://hibernate.atlassian.net/browse/HHH-7395
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		return hibernateJpaVendorAdapter;
	}

//s

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
