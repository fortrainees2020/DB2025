package com.example.demo;

import com.example.demo.controller.ProductController;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//Used to test only the web layer
@WebMvcTest(ProductController.class)
/*Automatically configures:
MockMvc (to simulate HTTP requests)
Spring MVC components (e.g., @ControllerAdvice, @JsonComponent)
Does NOT load:
Service layer
Repository layer
Full @ComponentScan
**/
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    //The ObjectMapper is a class from the Jackson library in Java used for serializing and
    // deserializing Java objects to and from JSON.
    private ObjectMapper objectMapper;

    //  Test the simple "/api/hello" endpoint to ensure it returns "Hi Spring" with status 200 OK
    @Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hi Spring"));}



    //  Test fetching all products from "/api/products" and ensure the response contains correct data
    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = new Product(1, "Laptop", 1000.0);
        Product p2 = new Product(2, "Phone", 500.0);

        Mockito.when(productService.getProductsFromDatabase()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].pname", is("Laptop")))
                .andExpect(jsonPath("$[1].price", is(500.0)));
    }



    //  Test fetching a specific product by ID (existing product) from "/api/products/db/1"
    @Test
    void testGetProductByIdFromDB_Success() throws Exception {
        Product product = new Product(1, "Monitor", 300.0);
        Mockito.when(productService.getProductById(1)).thenReturn(Optional.of(product));

        // $ refers to the root object of the JSON.
        mockMvc.perform(get("/api/products/db/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pname", is("Monitor")))
                .andExpect(jsonPath("$.price", is(300.0)));
    }

    //  Test fetching a product by non-existent ID (should return 404 Not Found)
    @Test
    void testGetProductByIdFromDB_NotFound() throws Exception {
        Mockito.when(productService.getProductById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/db/99"))
                .andExpect(status().isNotFound());
    }





    //  Test creating a new product using POST request and ensure the created product is returned
    @Test
    void testCreateProduct() throws Exception {
        Product newProduct = new Product(0, "Keyboard", 150.0); // Request Body
        Product savedProduct = new Product(10, "Keyboard", 150.0); //ResponseBody

        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.pname", is("Keyboard")));
    }


    //  Test updating an existing product and ensure the response contains updated data
    @Test
    void testUpdateProduct_Success() throws Exception {
        Product input = new Product(0, "Tablet", 800.0);
        Product updated = new Product(5, "Tablet", 800.0);

        Mockito.when(productService.updateProduct(eq(5), any(Product.class)))
                .thenReturn(org.springframework.http.ResponseEntity.ok(updated));

        mockMvc.perform(put("/api/products/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.pname", is("Tablet")));
    }

    //  Test deleting an existing product and ensure the correct success message is returned
    @Test
    void testDeleteProduct_Success() throws Exception {
        Mockito.when(productService.deleteProduct(3))
                .thenReturn(Map.of("Product has been Deleted", true));

        mockMvc.perform(delete("/api/products/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Product has been Deleted']", is(true)));
    }
}