package br.com.manysales.productapi.entities;

import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.supplier.SupplierRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter
@Entity
@Table(name = "TB_SUPPLIER")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    public static Supplier of(SupplierRequest request){
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(request, supplier);
        return supplier;
    }
}
