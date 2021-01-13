package com.greenlearner.product.repository;

import com.greenlearner.product.dto.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'Category.name':?0}")
    List<Product> findByCategory(String category);
}
