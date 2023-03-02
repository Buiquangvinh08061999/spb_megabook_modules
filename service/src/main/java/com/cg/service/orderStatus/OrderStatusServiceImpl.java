package com.cg.service.orderStatus;

import com.cg.domain.dto.OrderStatusDTO;
import com.cg.domain.entity.OrderStatus;
import com.cg.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderStatusServiceImpl implements IOrderStatusService {

    @Autowired
    private OrderStatusRepository orderStatusRepository;


    @Override
    public List<OrderStatus> findAll() {
        return null;
    }

    //1.Hiển thị tất cả danh sách OrderStatus ra
    @Override
    public List<OrderStatusDTO> findAllOrderStatusDTO() {
        return orderStatusRepository.findAllOrderStatusDTO();
    }

    @Override
    public Optional<OrderStatus> findById(Long id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public OrderStatus getById(Long id) {
        return null;
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
    //2.Cập nhật trạng thái đang xử lí, chỉ hiển thị hai trạng thái 2 và 3;
    @Override
    public List<OrderStatusDTO> findByTitleEnCOMPLETEDAndCANCEL(String pending) {
        return orderStatusRepository.findByTitleEnCOMPLETEDAndCANCEL(pending);
    }


}
