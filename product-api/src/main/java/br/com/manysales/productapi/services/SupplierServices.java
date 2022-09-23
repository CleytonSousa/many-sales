package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.ValidationException;
import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierRequest;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierResponse;
import br.com.manysales.productapi.entities.Supplier;
import br.com.manysales.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class SupplierServices {

    @Autowired
    private SupplierRepository repository;

    public Supplier findSupplierById(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("Supplier id not found for the given id"));
    }
    public SupplierResponse save(SupplierRequest supplierRequest){
        this.validateSupplierNameInformed(supplierRequest);
        Supplier supplier = repository.save(Supplier.of(supplierRequest));
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierNameInformed(SupplierRequest request){
        if(ObjectUtils.isEmpty(request.getName())){
            throw new ValidationException("Supplier name not present");
        }
    }
}
