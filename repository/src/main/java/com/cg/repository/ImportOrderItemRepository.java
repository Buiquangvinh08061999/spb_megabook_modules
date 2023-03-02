package com.cg.repository;

import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.dto.OrderItemDTO;
import com.cg.domain.entity.ImportOrder;
import com.cg.domain.entity.ImportOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportOrderItemRepository extends JpaRepository<ImportOrderItem, Long> {


    //2.Lấy tất cả orderItem theo OrderId truyền vào, dùng trong OrderService phương thức doChangeStatusOrder (api/order/updateStatus/{orderId})
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderItemDTO (" +
                "o.id, " +
                "o.productTitle, " +
                "o.productSku, " +
                "o.itemQuantity, " +
                "o.itemPrice, " +
                "o.totalPrice, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.item, " +
                "o.importOrder " +
            ") " +
            "FROM ImportOrderItem AS o " +
            "WHERE o.importOrder.id = ?1 "
    )
    List<ImportOrderItemDTO> findAllByOrderId(Long importOrderId);
}
