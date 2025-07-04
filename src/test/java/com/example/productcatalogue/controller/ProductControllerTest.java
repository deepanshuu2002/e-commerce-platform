package com.example.productcatalogue.controller;

import com.example.productcatalogue.model.Product;
import com.example.productcatalogue.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setup() {
        product = Product.builder()
                .name("iPhone")
                .category("Electronics")
                .price(699.99)
                .description("Latest iPhone")
                .build();
    }

    @Test
    public void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone"))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        when(productService.getProductById(999L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product toCreate = Product.builder().name("New Phone").category("Electronics").price(599.99).description("Nice phone").build();
        when(productService.createProduct(any(Product.class))).thenReturn(toCreate);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Phone\",\"category\":\"Electronics\",\"price\":599.99,\"description\":\"Nice phone\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Phone"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product updated = Product.builder().name("Updated Name").category("UpdatedCat").price(123.45).description("desc").build();
        when(productService.updateProduct(eq(2L), any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/api/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"category\":\"UpdatedCat\",\"price\":123.45,\"description\":\"desc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.price").value(123.45));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(
                Product.builder().name("A").category("B").price(10.0).build(),
                Product.builder().name("B").category("C").price(20.0).build()
        ));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].price").value(20.0));
    }

    @Test
    public void testSearchProducts() throws Exception {
        when(productService.searchProducts("Book", null, null, null))
                .thenReturn(Arrays.asList(Product.builder().name("Book1").category("Books").price(10.0).build()));
        mockMvc.perform(get("/api/products/search?name=Book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Book1"));
    }
}
