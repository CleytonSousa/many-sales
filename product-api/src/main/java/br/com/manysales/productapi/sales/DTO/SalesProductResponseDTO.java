package br.com.manysales.productapi.sales.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesProductResponseDTO {
    private List<String>  salesIds;
}
