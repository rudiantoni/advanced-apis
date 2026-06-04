package com.myapps.bavariamunich.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        myInitialization();
    }

    public void myInitialization() {
        logger.info("myInitialization started");

        logger.info("myInitialization finished");
    }

}
