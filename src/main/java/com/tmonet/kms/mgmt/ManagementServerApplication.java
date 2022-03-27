package com.tmonet.kms.mgmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.tmonet.common", "com.tmonet.kms", "com.tmonet.kmsp","com.tmonet.kmipmanager"})
public class ManagementServerApplication extends SpringBootServletInitializer{

	private static final Logger logger = LoggerFactory.getLogger(ManagementServerApplication.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ManagementServerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ManagementServerApplication.class, args);
		logger.info("##### START MANAGEMENT SERVER #####");
	}

}
