package br.com.manysales.productapi.entities.DTO.supplier;

import br.com.manysales.productapi.entities.Supplier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter @Setter
public class SupplierResponse {
    private Integer id;
    private String name;

    public static SupplierResponse of(Supplier supplier){
        SupplierResponse response = new SupplierResponse();
        BeanUtils.copyProperties(supplier, response);
        return response;
    }
}
