package br.com.manysales.productapi.entities.DTO.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    private String name;
    private Integer qtdAvailable;
    private Integer supplierId;
    private Integer categoryId;
}
