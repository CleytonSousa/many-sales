package br.com.manysales.productapi.services;

import br.com.manysales.productapi.config.exception.SucessResponse;
import br.com.manysales.productapi.config.exception.ValidationException;
import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServices {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    @Lazy
    private ProductServices productServices;

    public Category findCategoryById(Integer id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Category id not found for the given id"));
    }
    public CategoryResponse save(CategoryRequest categoryRequest){
        this.validateCategoryNameInformed(categoryRequest);
        Category category = categoryRepository.save(Category.of(categoryRequest));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest categoryRequest, Integer id){
        this.validateCategoryNameInformed(categoryRequest);
        Category category = Category.of(categoryRequest);
        category.setId(id);
        return CategoryResponse.of(categoryRepository.save(category));
    }

    public List<CategoryResponse> findByDescription(String description){
        if(ObjectUtils.isEmpty(description)){
            throw new ValidationException("Category description must be informed");
        }
        return categoryRepository.findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }
    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("Category id must be informed");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Id: "+id+" not found."));
        return CategoryResponse.of(category);
    }

    public SucessResponse delete(Integer id){
        this.validateInformedId(id);
        if(productServices.existsbySupplierId(id)){
            throw new ValidationException("You cannot delete this category, already in use");
        }
        categoryRepository.deleteById(id);
        return SucessResponse.create("Category deleted");
    }

    public void validateInformedId(Integer id){
        if(ObjectUtils.isEmpty(id)){
            throw new ValidationException("id cant by empty");
        }
    }

    private void validateCategoryNameInformed(CategoryRequest request){
        if(ObjectUtils.isEmpty(request.getDescription())){
            throw new ValidationException("category description not present");
        }
    }
}
