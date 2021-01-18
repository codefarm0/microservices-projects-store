package com.greenlearner.product.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@Configuration
public class ActuatorConfig {

    @Bean
    HttpTraceRepository httpTraceRepository(){
        return new InMemoryHttpTraceRepository();
    }
}
