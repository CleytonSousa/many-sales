package br.com.manysales.productapi.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductStockDTO implements Serializable {
    private String salesId;
    private List<ProductQuantityDTO> products;
}
