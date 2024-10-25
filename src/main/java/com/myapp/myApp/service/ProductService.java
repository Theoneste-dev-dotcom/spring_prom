package com.myapp.myApp.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myapp.myApp.model.Product;
import com.myapp.myApp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;
    @Autowired
    Product product;
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
    public Product getProductById(int id) {
       Optional<Product> product = repo.findById(id);
       if (product.isPresent()) {
           return product.get();
       }else {
           return null;
       }
    }
    public Product addProduct( Product product, MultipartFile image)throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return repo.save(product);
    }
    public String updateProduct(Product product, int id) {
        Optional<Product> existingProduct = repo.findById(id);

        if(existingProduct.isPresent()) {
            product.setId(id);
            repo.save(product);
        } else {
            System.out.println("Product not found");
        }
      return "Product "+ id + " updated successfully";
    }
    public String deleteProduct(int id) {
       Optional<Product> existingProduct = repo.findById(id);
       if(existingProduct.isPresent()) {
           repo.deleteById(id);
           return "Product deleted successfully";
       } else {
           System.out.println("Product not found");
       }
        return "Product deleted successfully";
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

}
