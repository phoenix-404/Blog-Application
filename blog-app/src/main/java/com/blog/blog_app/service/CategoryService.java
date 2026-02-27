package com.blog.blog_app.service;

import com.blog.blog_app.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

    //getbyid
    CategoryDTO getCategoryById(Integer categoryId);

    //getall
    List<CategoryDTO> getAllCategory();
}
