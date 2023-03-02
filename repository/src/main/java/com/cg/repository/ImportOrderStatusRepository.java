package com.cg.repository;

import com.cg.domain.dto.ImportOrderStatusDTO;
import com.cg.domain.dto.OrderStatusDTO;
import com.cg.domain.entity.ImportOrder;
import com.cg.domain.entity.ImportOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportOrderStatusRepository extends JpaRepository<ImportOrderStatus, Long> {

    //1.Hiển thị tất cả danh sách OrderStatus ra,  dùng hàm findAll của repo sẽ bị lỗi trùng lặp liên tục, phải viết hàm riêng DTO cho nó
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderStatusDTO (" +
                "o.id, " +
                "o.titleEn, " +
                "o.titleVi" +
            ") " +
            "FROM ImportOrderStatus AS o "
    )
    List<ImportOrderStatusDTO> findAllImportOrderStatusDTO();


    //2.Cập nhật trạng thái đang xử lí, chỉ hiển thị hai trạng thái 2 và 3;
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderStatusDTO (" +
                "o.id, " +
                "o.titleEn, " +
                "o.titleVi" +
            ") " +
            "FROM ImportOrderStatus AS o " +
            "WHERE o.titleEn <> ?1"
    )
    List<ImportOrderStatusDTO> findByTitleEnCOMPLETEDAndCANCEL(String pending);
}
