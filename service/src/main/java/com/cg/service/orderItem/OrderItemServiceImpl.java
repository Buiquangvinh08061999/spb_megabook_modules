package com.cg.service.orderItem;

import com.cg.domain.dto.OrderItemDTO;
import com.cg.domain.entity.Item;
import com.cg.domain.entity.Order;
import com.cg.domain.entity.OrderItem;
import com.cg.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public List<OrderItem> findAll() {
        return null;
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem getById(Long id) {
        return null;
    }

    //Lưu lại đối tượng orderItem
    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void remove(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public Optional<OrderItem> findByItemAndOrder(Item item, Order order) {
        return orderItemRepository.findByItemAndOrder(item , order);
    }

    //2.Tìm tất cả các trường orderItem theo orderId truyển vào
    @Override
    public List<OrderItemDTO> findAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
