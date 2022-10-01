package br.com.manysales.productapi.sales.DTO;

import br.com.manysales.productapi.sales.enums.SalesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesConfirmationDTO {
    private String salesID;
    private SalesStatus status;
}
