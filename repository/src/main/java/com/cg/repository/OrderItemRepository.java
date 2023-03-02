package com.cg.repository;

import com.cg.domain.dto.OrderItemDTO;
import com.cg.domain.entity.Item;
import com.cg.domain.entity.Order;
import com.cg.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    //1.Nếu order đã tồn tại, thì ta xử lí bên phần orderItem có 2 trường này, truyền vào 2 đối tượng item và order để xử lí;
    Optional<OrderItem> findByItemAndOrder(Item item, Order order);


    //2.Lấy tất cả orderItem theo OrderId truyền vào, dùng trong OrderService phương thức doChangeStatusOrder (api/order/updateStatus/{orderId})
    @Query("SELECT NEW com.cg.domain.dto.OrderItemDTO (" +
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
                "o.order " +
            ") " +
            "FROM OrderItem AS o " +
            "WHERE o.order.id = ?1 "
    )
    List<OrderItemDTO> findAllByOrderId(Long orderId);
}
