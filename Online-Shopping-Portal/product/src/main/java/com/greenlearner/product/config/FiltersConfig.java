package com.greenlearner.product.config;

import com.greenlearner.product.filters.RequestResponseLoggers;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
@Configuration
public class FiltersConfig {


    @Bean
    FilterRegistrationBean<RequestResponseLoggers> createLoggers(RequestResponseLoggers requestResponseLoggers){
        FilterRegistrationBean<RequestResponseLoggers> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(requestResponseLoggers);
        registrationBean.addUrlPatterns("/v1/addProduct","/v1/product/*", "/v1/productList/*");

        return registrationBean;
    }
}
