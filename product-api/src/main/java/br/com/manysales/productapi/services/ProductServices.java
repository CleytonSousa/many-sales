package br.com.manysales.productapi.services;

import br.com.manysales.config.exception.SucessResponse;
import br.com.manysales.config.exception.ValidationException;
import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.DTO.product.ProductRequest;
import br.com.manysales.productapi.entities.DTO.product.ProductResponse;
import br.com.manysales.productapi.entities.Product;
import br.com.manysales.productapi.entities.Supplier;
import br.com.manysales.productapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    @Lazy
    private SupplierServices supplierServices;

    @Autowired
    private CategoryServices categoryServices;

    public ProductResponse save(ProductRequest request){
        this.validateProductNameInformed(request);
        this.validateProductCategoryAndSupplierIdInformed(request);
        Category category = categoryServices.findCategoryById(request.getCategoryId());
        Supplier supplier = supplierServices.findSupplierById(request.getSupplierId());
        Product product = productRepository.save(Product.of(request,supplier,category));
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id){
        this.validateProductNameInformed(request);
        this.validateProductCategoryAndSupplierIdInformed(request);
        this.validateInformedId(id);
        Category category = categoryServices.findCategoryById(request.getCategoryId());
        Supplier supplier = supplierServices.findSupplierById(request.getSupplierId());
        Product product = Product.of(request,supplier,category);
        product.setId(id);
        return ProductResponse.of(productRepository.save(product));
    }

    public List<ProductResponse> findByName(String name){
        if(ObjectUtils.isEmpty(name)){
            throw new ValidationException("Product name must be informed");
        }
        return productRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
    public List<ProductResponse> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("Product id must be informed");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Id: "+id+" not found."));
        return ProductResponse.of(product);
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if (ObjectUtils.isEmpty(categoryId)) {
            throw new ValidationException("The product category ID name must be informed.");
        }
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if (ObjectUtils.isEmpty(supplierId)) {
            throw new ValidationException("The product supplier ID name must be informed.");
        }
        return productRepository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public SucessResponse delete(Integer id){
        this.validateInformedId(id);
        productRepository.deleteById(id);
        return SucessResponse.create("Product deleted");
    }

    public void validateInformedId(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("id cant by empty");
        }
    }

    public Boolean existsbyCategoryId(Integer id){
        return productRepository.existsByCategoryId(id);
    }
    public Boolean existsbySupplierId(Integer id){
        return productRepository.existsBySupplierId(id);
    }
    private void validateProductNameInformed(ProductRequest request){
        if(ObjectUtils.isEmpty(request.getName())){
            throw new ValidationException("product name not present");
        }
        if(ObjectUtils.isEmpty(request.getQtdAvailable())){
            throw new ValidationException("product quantity not present");
        }

        if(request.getQtdAvailable() < 0){
            throw new ValidationException("Quantity should by a positive number");
        }
    }

    private void validateProductCategoryAndSupplierIdInformed(ProductRequest request){
        if(ObjectUtils.isEmpty(request.getCategoryId())){
            throw new ValidationException("product category id not present");
        }
        if(ObjectUtils.isEmpty(request.getSupplierId())){
            throw new ValidationException("product supplier id not present");
        }
    }
}
