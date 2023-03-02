package com.cg.service.order;

import com.cg.domain.dto.*;
import com.cg.domain.entity.Customer;
import com.cg.domain.entity.Order;
import com.cg.domain.entity.OrderStatus;
import com.cg.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface IOrderService extends IGeneralService<Order> {


    //1. Hiển thị tất cả phần order dựa theo customer_id và trạng thái, Nếu khác trạng thái sẽ tạo đơn hàng mới);(api/order/add)
    Optional<Order> findOrderByCustomerIdAndTitleEn(Long id, String titleEn);

    //2.Hiển thị order theo trạng thái pending(đang xử lí);(api/order/listStatusPending)
    List<OrderDTO> findAllOrderDTOByOrderStatusPENDING(String pending);

    //.Hiển thị order theo trạng thái COMPLETED(đã hoàn thành)
    List<OrderDTO> findAllOrderDTOByOrderStatusCOMPLETED(String completed);

    //Hiển thị order theo trạng thái CANCEL(đã hủy đơn vận chuyển);(api/orders/listStatusCancel)
    List<OrderDTO> findAllOrderDTOByOrderStatusCANCEL(String cancel);


    //4.Chuyển trạng thái order, truyền vào orderId và orderStatusId lấy từ value option;(api/orders/update-status/{orderId})
    Order doChangeStatusOrder(Order order, OrderStatus orderStatus);



    //6.Tìm list order dựa vào customerId truyền vào, tính tổng tiền của customerId đó, với điệu kiện trạng thái phải hoàn thành 3 4 thì mới tính tổng
    List<OrderDTO> findByCustomerId(Long customerId,String pending, String cancel);


    //add order, a Minh hướng dẫn
    void createOrder(List<OrderItemDTO> orderItemDTOList, Customer customer);



    GrandTotalPendingDTO findGrandTotalOrderStatusPending(String pending); //tổng tiền đơn hàng đang xử lí
    CountDTO findCountOrderStatusPending(String pending); // SL đơn hàng

    GrandTotalPendingDTO findGrandTotalOrderStatusCompleted(String completed);
    CountDTO findCountOrderStatusCompleted(String completed);

    CountDTO findCountOrderStatusCancel(String cancel);

    //Tien
    List<OrderDTO> findAllOrdersDTO();


    //Vinh viết stored procedure
    List<IOrderDTO> findAllOrderDTOData();


    List<ITotalAnalytics> orderTotalToday();

    List<ITotalAnalytics> orderTotalWeek();

    List<ITotalAnalytics> orderTotalMonth();
}
