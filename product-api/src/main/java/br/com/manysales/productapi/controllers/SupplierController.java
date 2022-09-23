package br.com.manysales.productapi.controllers;

import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierRequest;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierResponse;
import br.com.manysales.productapi.services.CategoryServices;
import br.com.manysales.productapi.services.SupplierServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierServices supplierServices;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest supplierRequest){
        return supplierServices.save(supplierRequest);
    }
}
