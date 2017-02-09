package com.nikesh.musicplaylistapi.config;

import com.nikesh.musicplaylistapi.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Configuration
@PropertySource("file:${catalina.home}/conf/music-api-app.properties")
@EnableTransactionManagement
public class DataSourceConfig {

    private final Environment env;

    @Autowired
    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(env.getRequiredProperty(ApplicationConstants.DataSourceConstants.CONNECTION_URL));
        dataSource.setUsername(env.getRequiredProperty(ApplicationConstants.DataSourceConstants.CONNECTION_USER));
        dataSource.setPassword(env.getRequiredProperty(ApplicationConstants.DataSourceConstants.CONNECTION_PASS));
        dataSource.setDriverClassName(env.getRequiredProperty(ApplicationConstants.DataSourceConstants.CONNECTION_DRIVER_CLASS));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());

        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(ApplicationConstants.DataSourceConstants.ENTITY_MANAGER_PACKAGES_TO_SCAN));
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(ApplicationConstants.DataSourceConstants.HIBERNATE_DIALECT,
                env.getRequiredProperty(ApplicationConstants.DataSourceConstants.HIBERNATE_DIALECT));
        properties.put(ApplicationConstants.DataSourceConstants.HIBERNATE_SHOW_SQL,
                env.getRequiredProperty(ApplicationConstants.DataSourceConstants.HIBERNATE_SHOW_SQL));
        properties.setProperty(ApplicationConstants.DataSourceConstants.HIBERNATE_HBM2DDL_AUTO,
                env.getRequiredProperty(ApplicationConstants.DataSourceConstants.HIBERNATE_HBM2DDL_AUTO));
        return properties;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
