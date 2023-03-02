package com.cg.service.orderItem;

import com.cg.domain.dto.OrderItemDTO;
import com.cg.domain.entity.Item;
import com.cg.domain.entity.Order;
import com.cg.domain.entity.OrderItem;
import com.cg.service.IGeneralService;
import java.util.List;
import java.util.Optional;


public interface IOrderItemService extends IGeneralService<OrderItem> {

    //1.Kiểm tra
    Optional<OrderItem> findByItemAndOrder(Item item, Order order);


    //2.Tìm tất cả các trường orderItem theo orderId truyển vào
    List<OrderItemDTO> findAllByOrderId(Long orderId);
}
