/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amp.common.api.vision.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/** Entry point to running the Spring Boot application. */
@SpringBootApplication(scanBasePackages = { "com.amp.common.api.vision"})
@EntityScan(basePackages = {"com.amp.common.api.vision"})
public class Application extends SpringBootServletInitializer
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) 
	{
        return builder.sources(Application.class);
    }
	
	@Configuration
    //@Profile({ "default", "production" })
    @PropertySource("classpath:" + VisionApiConstants.PROPERTY_FILE_NAME1)
	@PropertySource("classpath:" + VisionApiConstants.PROPERTY_FILE_NAME2)
	static class ProductionConfiguration 
	{
        
    }
	
	public static void main(String[] args) 
	{
		SpringApplication sa = new SpringApplication(Application.class);
	    sa.run(args);
	}
}
