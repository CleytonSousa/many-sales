package br.com.manysales.productapi.entities.DTO.product;

import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierResponse;
import br.com.manysales.productapi.entities.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private Integer quantityAvailable;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    public static ProductResponse of(Product product){
       return ProductResponse.builder()
               .id(product.getId())
               .name(product.getName())
               .quantityAvailable(product.getQuantityAvailable())
               .createdAt(product.getCreatedAt())
               .category(CategoryResponse.of(product.getCategory()))
               .supplier(SupplierResponse.of(product.getSupplier()))
               .build();
    }
}
