package com.amp.common.api.vision.config;


import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class PersistenceConfig {

	    @Bean(name="dataSource")
	    //@Profile(value="production")
	    @ConfigurationProperties(prefix = "spring.datasource.pool")
	    public DataSource dataSource(DataSourceProperties properties) {
	        return properties.initializeDataSourceBuilder().type(ComboPooledDataSource.class).build();
	    }
		
}
