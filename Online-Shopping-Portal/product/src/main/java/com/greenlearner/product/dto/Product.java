package com.greenlearner.product.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Product name should not be null")
    private String name;

    @NotNull(message = "Category should not be null")
    private Category category;

    @Min(0)

    private double price;

    private String currency;
    @Max(100)
    private double discount;
    private String discountDescription;
    private List<String> imageURLs;
}
