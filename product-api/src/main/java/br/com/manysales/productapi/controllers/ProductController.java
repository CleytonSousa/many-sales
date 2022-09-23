package br.com.manysales.productapi.controllers;

import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.entities.DTO.product.ProductRequest;
import br.com.manysales.productapi.entities.DTO.product.ProductResponse;
import br.com.manysales.productapi.services.CategoryServices;
import br.com.manysales.productapi.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return productServices.save(request);
    }
}
