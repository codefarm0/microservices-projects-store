package com.greenlearner.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@Data
@AllArgsConstructor
public class ProductResponse {
    private String status;
    private String message;
}
