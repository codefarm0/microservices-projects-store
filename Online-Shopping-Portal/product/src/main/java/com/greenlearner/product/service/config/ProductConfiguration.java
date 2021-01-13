package com.greenlearner.product.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "product")
public class ProductConfiguration {

    private List<String> currencies;
}
