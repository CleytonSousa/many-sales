package br.com.manysales.productapi.entities.DTO.category;

import br.com.manysales.productapi.entities.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter @Setter
public class CategoryResponse {
    private Integer id;
    private String description;

    public static CategoryResponse of(Category category){
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        return response;
    }
}
