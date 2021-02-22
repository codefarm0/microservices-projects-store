package com.greenlearner.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String status;
    private String message;
}
