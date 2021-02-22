package com.greenlearner.product.exception;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
