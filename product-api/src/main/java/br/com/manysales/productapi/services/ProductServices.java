package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.ValidationException;
import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.product.ProductRequest;
import br.com.manysales.productapi.entities.DTO.product.ProductResponse;
import br.com.manysales.productapi.entities.Product;
import br.com.manysales.productapi.entities.Supplier;
import br.com.manysales.productapi.repositories.ProductRepository;
import br.com.manysales.productapi.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
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
