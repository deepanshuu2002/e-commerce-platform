
package com.example.productcatalogue.service;

import com.example.productcatalogue.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> searchProducts(String name, String category, Double minPrice, Double maxPrice);
}
