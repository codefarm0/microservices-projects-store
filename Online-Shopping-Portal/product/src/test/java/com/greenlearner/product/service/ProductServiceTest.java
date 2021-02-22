package com.greenlearner.product.service;

import com.greenlearner.product.dto.Product;
import com.greenlearner.product.dto.ProductResponse;
import com.greenlearner.product.exception.CurrencyNotValidException;
import com.greenlearner.product.exception.OfferNotValidException;
import com.greenlearner.product.exception.ProductNotFoundException;
import com.greenlearner.product.repository.ProductRepository;
import com.greenlearner.product.service.config.ProductConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
class ProductServiceTest {
    private ProductRepository productRepository;

    private ProductConfiguration productConfiguration;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productConfiguration = new ProductConfiguration();
        productConfiguration.setCurrencies(List.of("INR", "USD"));
        productService = new ProductService(productRepository, productConfiguration);
    }

    @Nested
    @DisplayName("All the test cases for adding product into the system")
    class AddProductTestScenarios {

        @Test
        @DisplayName("Offer not valid scenario")
        void addProductOfferNotValidException() {
            Product product = new Product();
            product.setPrice(0.0);
            product.setDiscount(10);
            assertThrows(OfferNotValidException.class, () -> productService.addProduct(product));
        }

        @Test
        @DisplayName("Currency not valid scenario")
        void addProductCurrencyNotValidException() {
            Product product = new Product();
            product.setPrice(10000.0);
            product.setDiscount(10);
            product.setCurrency("HKD");
            assertThrows(CurrencyNotValidException.class, () -> productService.addProduct(product));
        }

        @Test
        @DisplayName("Happy Path - product added into the system")
        void addProductIntoTheSystem() {
            Product product = new Product();
            product.setName("Mi TV");
            product.setPrice(10000.0);
            product.setDiscount(10);
            product.setCurrency("INR");

            when(productRepository.save(any(Product.class))).thenReturn(product);

            ProductResponse actualResponse = productService.addProduct(product);

            ProductResponse expectedResponse = new ProductResponse("success", product.getName() + "added into the system");

            assertThat(expectedResponse).isEqualTo(actualResponse);

        }

    }

    @Nested
    @DisplayName("All the cases to fetch products list from the system")
    class ListAllProductsFromTheSystem {

        @Test
        @DisplayName("No products found in the system")
        void listAllProductsInCaseOfEmptyResult() {
            when(productRepository.findAll()).thenReturn(Collections.emptyList());
            assertThrows(ProductNotFoundException.class, () -> productService.listAllProducts());
        }

        @Test
        @DisplayName("happy path - products found in the system")
        void listAllProductsFromTheSystem() {
            when(productRepository.findAll()).thenReturn(List.of(new Product(), new Product()));
            List<Product> products = productService.listAllProducts();

            assertThat(products).isNotNull();
            assertThat(2).isEqualTo(products.size());
        }

        @Test
        @DisplayName("Product not found for specific category")
        void productCategoryListEmptyTest() {
            when(productRepository.findByCategory(anyString())).thenReturn(Collections.emptyList());
            assertThrows(ProductNotFoundException.class, () -> productService.productCategoryList("Car"));
        }

        @Test
        @DisplayName("Product found for specific category")
        void productCategoryListFromTheSystemTest() {
            when(productRepository.findByCategory(anyString())).thenReturn(List.of(new Product(), new Product()));
            List<Product> products = productService.productCategoryList("Car");
            assertThat(products).isNotNull();
            assertThat(2).isEqualTo(products.size());
        }
    }

    @Nested()
    @DisplayName("All test cases for product to be searched by given Id")
    class ProductByIdTestScenarios {

        @Test
        @DisplayName("Product not found")
        void productByIdNotFoundExceptionTest() {
            assertThrows(ProductNotFoundException.class, () -> productService.productById("random-id"));
        }

        @Test
        @DisplayName("Product found for given id")
        void productByIdTest() {
            Product product = new Product();
            product.setName("Mi TV");
            product.setPrice(10000.0);
            product.setDiscount(10);
            product.setCurrency("INR");

            when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
            Product actualProduct = productService.productById("random-id");
            assertThat(product).isEqualTo(actualProduct);
        }
    }

    @Nested
    @DisplayName("All the test cases for update product")
    class UpdateProductTestScenarios {

        @Test
        @DisplayName("Product to be updated not found")
        void updateProduct_NotFound() {
            Product product = new Product();
            product.setName("Mi TV");
            product.setPrice(10000.0);
            product.setDiscount(10);
            product.setCurrency("INR");

            ProductResponse actualResponse = productService.updateProduct(product);

            ProductResponse expectedResponse = new ProductResponse("FAILED", "Product to be updated not found in the system");

            assertThat(expectedResponse).isEqualTo(actualResponse);
        }

        @Test
        @DisplayName("Product updated - happy path")
        void updateProduct() {
            Product product = new Product();
            product.setId("some-random-id");
            product.setName("Mi TV");
            product.setPrice(10000.0);
            product.setDiscount(10);
            product.setCurrency("INR");

            when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

            Product newProduct = new Product();
            newProduct.setId(product.getId());
            newProduct.setName("Mi TV New");

            when(productRepository.save(any(Product.class))).thenReturn(newProduct);

            ProductResponse actualResponse = productService.updateProduct(newProduct);

            ProductResponse expectedResponse = new ProductResponse("SUCCESS", "Product Updated - " + newProduct.getName());

            assertThat(expectedResponse).isEqualTo(actualResponse);
        }

    }

    @Nested
    @DisplayName("All the test cases for delete product")
    class DeletedProductTestScenarios {
        @Test
        @DisplayName("Product to be deleted not found")
        void deleteProduct_NotFound() {

            ProductResponse actualResponse = productService.deleteProductById("some-random-id");

            ProductResponse expectedResponse = new ProductResponse("FAILED", "Product to be deleted not found in the system");

            assertThat(expectedResponse).isEqualTo(actualResponse);
        }

        @Test
        @DisplayName("Product deleted - happy path")
        void deleteProduct() {

            when(productRepository.findById(anyString())).thenReturn(Optional.of(new Product()));

            ProductResponse actualResponse = productService.deleteProductById("some-random-id");

            ProductResponse expectedResponse = new ProductResponse("SUCCESS", "Product Deleted");

            assertThat(expectedResponse).isEqualTo(actualResponse);

            verify(productRepository, times(1)).deleteById("some-random-id");
        }
    }
}