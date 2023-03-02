package com.cg.api;

import com.cg.domain.dto.CategoryDTO;
import com.cg.domain.entity.Category;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICategoryService categoryService;

    //Lấy tất cả danh mục của catelory ra;
    @GetMapping
        public ResponseEntity<?> findAllCategoryDTO() {
        List<CategoryDTO> categoryDTOList = categoryService.findAllCategoryDTO();

        if (categoryDTOList.isEmpty()) {
            throw new DataInputException("Danh mục rỗng không có dữ liệu, vui lòng kiểm tra lại!");
        }

        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCategoryDTOById(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTOOptional = categoryService.findCategoriesById(id);

        if (!categoryDTOOptional.isPresent()) {
            throw new DataInputException("Danh mục rỗng không có dữ liệu, vui lòng kiểm tra lại!");
        }

        return new ResponseEntity<>(categoryDTOOptional.get(), HttpStatus.OK);
    }



    //Tạo mới danh muc
    @PostMapping("/create")
    public ResponseEntity<?> doCreateCategory(@RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existsByTitle = categoryService.existsByTitle(categoryDTO.getTitle());
        if (existsByTitle) {
            throw new EmailExistsException("Tên Title danh mục đã tồn tại, vui lòng nhập tên khác!");
        }

        categoryDTO.setId(0L);

        String slug = appUtils.replaceNonEnglishChar(categoryDTO.getTitle());
        slug = appUtils.removeNonAlphanumeric(slug);
        categoryDTO.setSlug(slug);

        try {
            Category category = categoryService.save(categoryDTO.toCategory()); //Save là tên hàm , ta truyền vào để qua bên save dữ liệu mới vào

            return new ResponseEntity<>(category.toCategoryDTO(), HttpStatus.CREATED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }

    }

}
