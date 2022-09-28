package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.SucessResponse;
import br.com.manysales.productapi.config.exception.ValidationException;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierRequest;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierResponse;
import br.com.manysales.productapi.entities.Supplier;
import br.com.manysales.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServices {

    @Autowired
    private SupplierRepository repository;
    @Autowired
    private ProductServices productServices;

    public Supplier findSupplierById(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("Supplier id not found for the given id"));
    }

    public List<SupplierResponse> findByName(String name){
        if(ObjectUtils.isEmpty(name)){
            throw new ValidationException("Supplier name must be informed");
        }
        return repository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }
    public List<SupplierResponse> findAll(){
        return repository.findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id){
        this.validateInformedId(id);
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new ValidationException("Id: "+id+" not found."));
        return SupplierResponse.of(supplier);
    }

    public SucessResponse delete(Integer id){
        this.validateInformedId(id);
        if(productServices.existsbySupplierId(id)){
            throw new ValidationException("You cannot delete this supplier, already in use");
        }
        repository.deleteById(id);
        return SucessResponse.create("Supplier deleted");
    }

    public void validateInformedId(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("id cant by empty");
        }
    }

    public SupplierResponse save(SupplierRequest supplierRequest){
        this.validateSupplierNameInformed(supplierRequest);
        Supplier supplier = repository.save(Supplier.of(supplierRequest));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest supplierRequest, Integer id){
        this.validateSupplierNameInformed(supplierRequest);
        Supplier supplier = Supplier.of(supplierRequest);
        supplier.setId(id);
        return SupplierResponse.of(repository.save(supplier));
    }

    private void validateSupplierNameInformed(SupplierRequest request){
        if(ObjectUtils.isEmpty(request.getName())){
            throw new ValidationException("Supplier name not present");
        }
    }
}
