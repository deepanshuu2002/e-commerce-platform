package com.example.productcatalogue.service;

import com.example.productcatalogue.model.Product;
import com.example.productcatalogue.repository.ProductRepository;
import com.example.productcatalogue.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = Product.builder().name("Test").category("Cat").price(99.99).build();
        when(repository.save(product)).thenReturn(product);

        Product result = service.createProduct(product);

        assertEquals("Test", result.getName());
        assertEquals("Cat", result.getCategory());
    }

    @Test
    public void testGetAllProducts() {
        List<Product> mockList = Arrays.asList(
                Product.builder().name("A").category("X").price(10.0).build(),
                Product.builder().name("B").category("Y").price(20.0).build()
        );
        when(repository.findAll()).thenReturn(mockList);

        List<Product> result = service.getAllProducts();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetProductByIdFound() {
        Product product = Product.builder().name("Test").category("Cat").price(50.0).build();
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = service.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getName());
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        Optional<Product> result = service.getProductById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateProductSuccessfully() {
        Product existing = Product.builder().name("Old").category("A").price(1.0).build();
        Product update = Product.builder().name("New").category("B").price(2.0).build();
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.updateProduct(1L, update);
        assertEquals("New", result.getName());
        assertEquals("B", result.getCategory());
        assertEquals(2.0, result.getPrice());
    }

    @Test
    public void testUpdateProductNotFoundThrows() {
        Product update = Product.builder().name("Update").category("X").price(99.0).build();
        when(repository.findById(77L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.updateProduct(77L, update));
        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test
    public void testSearchProductsReturnsEmptyList() {
        when(repository.search("noexist", null, null, null)).thenReturn(Arrays.asList());
        List<Product> result = service.searchProducts("noexist", null, null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteProduct() {
        service.deleteProduct(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProductDoesNotThrowWhenAbsent() {
        // Should not throw even if the id does not exist
        doNothing().when(repository).deleteById(123L);
        assertDoesNotThrow(() -> service.deleteProduct(123L));
    }
}
