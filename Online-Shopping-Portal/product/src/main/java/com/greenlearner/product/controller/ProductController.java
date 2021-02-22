package com.greenlearner.product.controller;

import com.greenlearner.product.dto.Product;
import com.greenlearner.product.dto.ProductResponse;
import com.greenlearner.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@RestController
@RequestMapping("/v1")
@Api(description = "Product API having endpoints which are used to interact with product micrservice")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    @ApiOperation("Used to add the product into system")
    ResponseEntity<ProductResponse> addProduct(@ApiParam("Information about products to be added") @RequestBody @Valid Product product) {

        ProductResponse productResponse = productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/productList")
    @ApiOperation("List all the products into the system")
    List<Product> productList() {
        return productService.listAllProducts();
    }

    @GetMapping("/productList/{category}")
    List<Product> productCategoryList(@ApiParam("category of the products to be listed") @PathVariable String category) {
        return productService.productCategoryList(category);
    }

    @GetMapping("/product/{id}")
    Product productById(@PathVariable String id) {
        return productService.productById(id);
    }

    @PutMapping("/productUpdate")
    ProductResponse updateProduct(@RequestBody Product product) {

        return productService.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    ProductResponse deleteProductById(@PathVariable String id) {
        return productService.deleteProductById(id);
    }
}
