package br.com.manysales.productapi.controllers;

import br.com.manysales.productapi.config.exception.SucessResponse;
import br.com.manysales.productapi.entities.DTO.category.CategoryRequest;
import br.com.manysales.productapi.entities.DTO.category.CategoryResponse;
import br.com.manysales.productapi.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest categoryRequest){
        return categoryServices.save(categoryRequest);
    }

    @GetMapping()
    public List<CategoryResponse> findAll(){
        return categoryServices.findAll();
    }

    @GetMapping("/description")
    public List<CategoryResponse> findByDescription(@RequestParam String description){
        return categoryServices.findByDescription(description);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryServices.findById(id);
    }

    @DeleteMapping("/{id}")
    public SucessResponse delete(@PathVariable Integer id){
        return categoryServices.delete(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Integer id, @RequestBody CategoryRequest categoryRequest){
        return categoryServices.update(categoryRequest, id);
    }
}
