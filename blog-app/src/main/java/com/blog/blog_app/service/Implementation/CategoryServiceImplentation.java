package com.blog.blog_app.service.Implementation;

import com.blog.blog_app.entity.Category;
import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.payload.CategoryDTO;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplentation implements CategoryService {

    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;

    public CategoryServiceImplentation(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO,Category.class);
        Category newCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(newCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory,CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category ID", categoryId));

        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));

         return this.modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();

        List<CategoryDTO> categoryDTOs = categories.stream().map((category) -> this.modelMapper.map(category,CategoryDTO.class)).toList();
        return categoryDTOs;
    }

}
