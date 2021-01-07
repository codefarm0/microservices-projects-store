package com.greenlearner.product.dto;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class Category {

    private Integer id;
    private String name;
    private String brand;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
