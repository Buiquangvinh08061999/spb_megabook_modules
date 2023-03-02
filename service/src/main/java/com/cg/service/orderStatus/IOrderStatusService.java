package com.cg.service.orderStatus;

import com.cg.domain.dto.OrderStatusDTO;
import com.cg.domain.entity.OrderStatus;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IOrderStatusService extends IGeneralService<OrderStatus> {

    //1.Hiển thị tất cả danh sách OrderStatus ra
    List<OrderStatusDTO> findAllOrderStatusDTO();

    //2.Cập nhật trạng thái đang xử lí, chỉ hiển thị hai trạng thái 2 và 3;
    List<OrderStatusDTO> findByTitleEnCOMPLETEDAndCANCEL(String pending);



}
