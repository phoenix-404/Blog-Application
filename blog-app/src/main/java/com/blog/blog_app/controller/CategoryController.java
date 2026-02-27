package com.blog.blog_app.controller;


import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.CategoryDTO;
import com.blog.blog_app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //POST - Create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createCategoryDTO = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createCategoryDTO, HttpStatus.CREATED);
    }

    //PUT-Update Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      @PathVariable("categoryId") Integer categoryId){
        CategoryDTO updatedCategoryDTO = this.categoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.ok(updatedCategoryDTO);
    }
    //Delete - Delete Category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully !!", true), HttpStatus.OK);
    }
    //GetAll - retrieve all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }

    //GetByID - retrieve Category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId){
        return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
    }

}
