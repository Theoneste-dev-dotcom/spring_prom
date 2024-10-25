package com.myapp.myApp.controllers;

import com.myapp.myApp.model.Product;
import com.myapp.myApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductControllers {
    @Autowired
    ProductService service;

    @GetMapping("/")
    @CrossOrigin
    public String greet() {
        return "Hello World";
    }

    @GetMapping("/products")
    @CrossOrigin
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/product/{prodId}")
    @CrossOrigin
    public Product getProductById(@PathVariable int prodId) {
        return service.getProductById(prodId);
    }

    @PostMapping("/product")
    @CrossOrigin
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {

        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     @GetMapping("/product/{prodId}/image")
     public ResponseEntity<byte[]> getProductImage(@PathVariable int prodId) {
        Product product = service.getProductById(prodId);
        byte[] imageFile = product.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
     }


    @PutMapping("/product/{prodId}")
    @CrossOrigin
    public String updateProduct(@PathVariable int prodId, @RequestBody Product product) {
        return service.updateProduct(product, prodId);
    }

    @DeleteMapping("/product/{prodId}")
    @CrossOrigin
    public void deleteProduct(@PathVariable int prodId) {
        service.deleteProduct(prodId);
    }
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products =  service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
