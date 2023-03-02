package com.cg.repository;

import com.cg.domain.dto.CategoryDTO;
import com.cg.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //1.Hiển thị tất cả danh sách Category
    @Query("SELECT NEW com.cg.domain.dto.CategoryDTO (" +
                "c.id, " +
                "c.slug, " +
                "c.title" +
            ") " +
            "FROM Category AS c"
    )
    List<CategoryDTO> findAllCategoryDTO();


    //2.Kiểm tra điều kiện khi create, thêm mới
    Boolean existsByTitle(String title);

    Optional<CategoryDTO> findCategoriesById(Long id);
}
