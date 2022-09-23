package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.ValidationException;
import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.entities.Supplier;
import br.com.manysales.productapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findCategoryById(Integer id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Category id not found for the given id"));
    }
    public CategoryResponse save(CategoryRequest categoryRequest){
        this.validateCategoryNameInformed(categoryRequest);
        Category category = categoryRepository.save(Category.of(categoryRequest));
        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request){
        if(ObjectUtils.isEmpty(request.getDescription())){
            throw new ValidationException("category description not present");
        }
    }
}
