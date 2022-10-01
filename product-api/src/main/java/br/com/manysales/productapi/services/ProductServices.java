package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.SucessResponse;
import br.com.manysales.productapi.config.exception.ValidationException;
import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.DTO.ProductCheckStockRequest;
import br.com.manysales.productapi.entities.DTO.ProductQuantityDTO;
import br.com.manysales.productapi.entities.DTO.ProductStockDTO;
import br.com.manysales.productapi.entities.DTO.product.ProductRequest;
import br.com.manysales.productapi.entities.DTO.product.ProductResponse;
import br.com.manysales.productapi.entities.DTO.product.ProductSalesResponse;
import br.com.manysales.productapi.entities.Product;
import br.com.manysales.productapi.entities.Supplier;
import br.com.manysales.productapi.repositories.ProductRepository;
import br.com.manysales.productapi.sales.DTO.SalesConfirmationDTO;
import br.com.manysales.productapi.sales.DTO.SalesProductResponseDTO;
import br.com.manysales.productapi.sales.client.SalesClient;
import br.com.manysales.productapi.sales.enums.SalesStatus;
import br.com.manysales.productapi.sales.rabbitmq.SalesConfirmationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    @Lazy
    private SupplierServices supplierServices;
    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    @Autowired
    private SalesClient salesClient;

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

    public void updateProductStock(ProductStockDTO productStockDTO){
        try{
            this.validateStockUpdateData(productStockDTO);
            this.updateStock(productStockDTO);
        } catch (Exception e){
            log.error("Error ProductService.updateProductStock: {}", e.getMessage(), e);
            salesConfirmationSender.sendSalesConfirmationMessage(
                    new SalesConfirmationDTO(productStockDTO.getSalesId(), SalesStatus.REJECTED)
            );
        }
    }

    @Transactional
    private void updateStock(ProductStockDTO productStockDTO){
        List<Product> productsToUpdate = new ArrayList<>();
        productStockDTO.getProducts().forEach(
                x -> {
                    Product productdb = productRepository.findById(x.getProductId())
                            .orElseThrow(() -> new ValidationException("Product not found to ID: " + x.getProductId()));
                    if(x.getQuantity() > productdb.getQuantityAvailable()){
                        throw new ValidationException(
                                String.format("the product %s out of stock, is stock only: %s", productdb.getId(), productdb.getQuantityAvailable())
                        );
                    }
                    productdb.updateStock(x.getQuantity());
                    productsToUpdate.add(productdb);
                });
        if(!productsToUpdate.isEmpty()){
            productRepository.saveAll(productsToUpdate);
            SalesConfirmationDTO confirmationMessage = new SalesConfirmationDTO(productStockDTO.getSalesId(), SalesStatus.APROVED);
            salesConfirmationSender.sendSalesConfirmationMessage(confirmationMessage);
        }
    }

    private void validateStockUpdateData(ProductStockDTO productStockDTO){
        if(ObjectUtils.isEmpty(productStockDTO) || ObjectUtils.isEmpty(productStockDTO.getSalesId())){
            throw new ValidationException("The product data or sales id is null");
        }
        if(ObjectUtils.isEmpty(productStockDTO.getProducts())){
            throw new ValidationException("The sales products must be informed");
        }
        productStockDTO.getProducts().forEach(
                x -> {
                    if(ObjectUtils.isEmpty(x.getQuantity()) || ObjectUtils.isEmpty(x.getProductId())){
                        throw new ValidationException("The product ID and Qautity must be informed.");
                    }
                }
        );
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

    public void validateProductIsFind(Product product){
        if(ObjectUtils.isEmpty(product)){
            throw new ValidationException("The product is not found");
        }
    }

    public ProductSalesResponse findProductSales(Integer id){
        Optional<Product> product = productRepository.findById(id);
        this.validateProductIsFind(product.get());
        try{
            SalesProductResponseDTO sales = salesClient.findSalesByProductId(product.get().getId()).orElseThrow(() -> new ValidationException("The sales was not found by this product"));
            return ProductSalesResponse.of(product.get(), sales.getSalesIds());
        }catch (Exception e){
            throw new ValidationException("There was an error trying to get the product's sales");
        }
    }

    public SucessResponse checkProductStock(ProductCheckStockRequest productCheckStockRequest){
        if(ObjectUtils.isEmpty(productCheckStockRequest) || ObjectUtils.isEmpty(productCheckStockRequest.getProducts())){
            throw new ValidationException("The request data can't be null");
        }
        productCheckStockRequest
                .getProducts()
                .forEach(this::validateStock);
        return SucessResponse.create("Stock is ok!");
    }

    private void validateStock(ProductQuantityDTO productQuantityDTO){
        if(ObjectUtils.isEmpty(productQuantityDTO.getProductId()) || ObjectUtils.isEmpty(productQuantityDTO.getQuantity())){
            throw new ValidationException("Product ID and Quantity must be informed");
        }
        Product product = productRepository.findById(productQuantityDTO.getProductId()).get();
        if(productQuantityDTO.getQuantity() > product.getQuantityAvailable()){
            throw new ValidationException(String.format("The product %s is out of stock", product.getId()));
        }
    }
}
