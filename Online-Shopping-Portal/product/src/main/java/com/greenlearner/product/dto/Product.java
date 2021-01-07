package com.greenlearner.product.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "product")
public class Product {

    @Id
    private Integer id;
    private String name;
    private Category category;
    private double price;
    private String currency;
    private double discount;
    private String discountDescription;
    private List<String> imageURLs;
}
