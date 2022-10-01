package br.com.manysales.productapi.controllers;

import br.com.manysales.productapi.config.exception.SucessResponse;
import br.com.manysales.productapi.entities.DTO.ProductCheckStockRequest;
import br.com.manysales.productapi.entities.DTO.ProductStockDTO;
import br.com.manysales.productapi.entities.DTO.product.ProductRequest;
import br.com.manysales.productapi.entities.DTO.product.ProductResponse;
import br.com.manysales.productapi.entities.DTO.product.ProductSalesResponse;
import br.com.manysales.productapi.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return productServices.save(request);
    }

    @GetMapping()
    public List<ProductResponse> findAll(){
        return productServices.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id){
        return productServices.findByIdResponse(id);
    }

    @GetMapping("/name")
    public List<ProductResponse> findByName(@RequestParam String name){
        return productServices.findByName(name);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Integer id, @RequestBody ProductRequest productRequest){
        return productServices.update(productRequest, id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@RequestParam Integer categoryId){
        return productServices.findByCategoryId(categoryId);
    }

    @DeleteMapping("/{id}")
    public SucessResponse delete(@PathVariable Integer id){
        return productServices.delete(id);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId){
        return productServices.findBySupplierId(supplierId);
    }

    @PostMapping("/check-stock")
    public SucessResponse checkProductStock(@RequestBody ProductCheckStockRequest productStockDTO){
        return productServices.checkProductStock(productStockDTO);
    }

    @GetMapping("{productId}/sales")
    public ProductSalesResponse findProductSales(@PathVariable Integer productId){
        return productServices.findProductSales(productId);
    }
}
