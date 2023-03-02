package com.cg.service.category;

import com.cg.domain.dto.CategoryDTO;
import com.cg.domain.entity.Category;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;


public interface ICategoryService extends IGeneralService<Category> {

    //1.Hiển thị tất cả danh sách
    List<CategoryDTO> findAllCategoryDTO();

    //2.Kiểm tra điều kiện khi create, thêm mới
    Boolean existsByTitle(String title);

    Optional<CategoryDTO> findCategoriesById(Long id);
}
