package com.cg.service.category;

import com.cg.domain.dto.CategoryDTO;
import com.cg.domain.entity.Category;
import com.cg.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    //1.Hiển thị tất cả danh sách
    @Override
    public List<CategoryDTO> findAllCategoryDTO() {
        return categoryRepository.findAllCategoryDTO();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category getById(Long id) {
        return null;
    }

    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return categoryRepository.existsByTitle(title);
    }

    @Override
    public Optional<CategoryDTO> findCategoriesById(Long id) {
        return categoryRepository.findCategoriesById(id);
    }
}
