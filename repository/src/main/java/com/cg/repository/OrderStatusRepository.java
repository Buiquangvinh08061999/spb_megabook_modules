package com.cg.repository;

import com.cg.domain.dto.OrderStatusDTO;
import com.cg.domain.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    //1.Hiển thị tất cả danh sách OrderStatus ra,  dùng hàm findAll của repo sẽ bị lỗi trùng lặp liên tục, phải viết hàm riêng DTO cho nó
    @Query("SELECT NEW com.cg.domain.dto.OrderStatusDTO (" +
                "o.id, " +
                "o.titleEn, " +
                "o.titleVi" +
            ") " +
            "FROM OrderStatus AS o "
    )
    List<OrderStatusDTO> findAllOrderStatusDTO();



    //2.Cập nhật trạng thái đang xử lí, chỉ hiển thị hai trạng thái 2 và 3;
    @Query("SELECT NEW com.cg.domain.dto.OrderStatusDTO( " +
                "o.id, " +
                "o.titleEn, " +
                "o.titleVi" +
            ") " +
            "FROM OrderStatus AS o " +
            "WHERE o.titleEn <> ?1")
    List<OrderStatusDTO> findByTitleEnCOMPLETEDAndCANCEL(String pending);



}
