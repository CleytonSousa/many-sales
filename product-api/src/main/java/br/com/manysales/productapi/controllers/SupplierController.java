package br.com.manysales.productapi.controllers;

import br.com.manysales.config.exception.SucessResponse;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierRequest;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierResponse;
import br.com.manysales.productapi.services.SupplierServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierServices supplierServices;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest supplierRequest){
        return supplierServices.save(supplierRequest);
    }

    @GetMapping("/{id}")
    public SupplierResponse findById(@PathVariable Integer id){
        return supplierServices.findByIdResponse(id);
    }

    @GetMapping("/name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name){
        return supplierServices.findByName(name);
    }

    @PutMapping("/{id}")
    public SupplierResponse update(@PathVariable Integer id, @RequestBody SupplierRequest supplierRequest){
        return supplierServices.update(supplierRequest, id);
    }
    @DeleteMapping("/{id}")
    public SucessResponse delete(@PathVariable Integer id){
        return supplierServices.delete(id);
    }
}
