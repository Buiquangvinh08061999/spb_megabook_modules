package com.cg.repository;

import com.cg.domain.dto.*;
import com.cg.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //1.Phần add order:Hiển thị tất cả phần order dựa theo customer_id và trạng thái, Nếu khác trạng thái sẽ tạo đơn hàng mới
    @Query("SELECT NEW com.cg.domain.dto.OrderDTO (" +
                "o.id, " +
                "o.grandTotal, " +
                "o.describe, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.customer, " +
                "o.orderStatus" +
            ") " +
            "FROM Order AS o " +
            "WHERE o.customer.id = ?1 " +
            "AND o.orderStatus.titleEn = ?2 " +
            "ORDER BY o.id DESC"
    )
    Optional<Order> findOrderByCustomerIdAndTitleEn(Long id, String titleEn);


    //2.Hiển thị order theo trạng thái PENDING(đang xử lí)
    @Query("SELECT NEW com.cg.domain.dto.OrderDTO (" +
                "o.id, " +
                "o.grandTotal, " +
                "o.describe, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.customer, " +
                "o.orderStatus" +
            ") " +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    List<OrderDTO> findAllOrderDTOByOrderStatusPENDING(String pending);


    //.Hiển thị order theo trạng thái COMPLETED(đã hoàn thành)
    @Query("SELECT NEW com.cg.domain.dto.OrderDTO (" +
            "o.id, " +
            "o.grandTotal, " +
            "o.describe, " +
            "o.createAt, " +
            "o.createBy, " +
            "o.updateAt, " +
            "o.updateBy, " +
            "o.customer, " +
            "o.orderStatus" +
            ") " +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    List<OrderDTO> findAllOrderDTOByOrderStatusCOMPLETED(String completed);



    //4.Hiển thị order theo trạng thái CANCEL(Đã Hủy đơn hàng )
    @Query("SELECT NEW com.cg.domain.dto.OrderDTO (" +
                "o.id, " +
                "o.grandTotal, " +
                "o.describe, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.customer, " +
                "o.orderStatus" +
            ") " +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    List<OrderDTO> findAllOrderDTOByOrderStatusCANCEL(String cancel);



    //Hiển thị tổng tiền trong show Customer Khách hàng
    //6.Tìm list order dựa vào customerId truyền vào, tính tổng tiền của customerId đó, với điệu kiện trạng thái phải hoàn thành 2 thì mới tính tổng
    @Query("SELECT NEW com.cg.domain.dto.OrderDTO (" +
                "o.id, " +
                "o.grandTotal, " +
                "o.describe, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.customer, " +
                "o.orderStatus" +
            ") " +
            "FROM Order AS o " +
            "WHERE o.customer.id = ?1 " +
            "AND o.orderStatus.titleEn <> ?2 " +
            "AND o.orderStatus.titleEn <> ?3 "
    )
    List<OrderDTO> findByCustomerId(Long customerId, String pending, String cancel);

    //Tien


    //Tổng tiền theo trang trạng thái đang  chờ xử lí
    @Query("SELECT NEW com.cg.domain.dto.GrandTotalPendingDTO ( " +
                "SUM (o.grandTotal)" +
            ")" +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    GrandTotalPendingDTO findGrandTotalOrderStatusPending(String pending); //Tính tổng tiền đơn hàng trạng thái pending


    //đếm SL đơn hàng theo trang trạng thái đang  chờ xử lí
    @Query("SELECT NEW com.cg.domain.dto.CountDTO ( " +
                "COUNT (o.id) " +
            ")" +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    CountDTO findCountOrderStatusPending(String pending);


    //Tổng tiền theo trang trạng thái đang đã hoàn thành
    @Query("SELECT NEW com.cg.domain.dto.GrandTotalPendingDTO ( " +
             "SUM (o.grandTotal)" +
            ")" +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    GrandTotalPendingDTO findGrandTotalOrderStatusCompleted(String completed); //Tính tổng tiền đơn hàng trạng thái completed

    //đếm SL đơn hàng theo trang trạng thái đang  chờ xử lí
    @Query("SELECT NEW com.cg.domain.dto.CountDTO ( " +
                "COUNT (o.id) " +
            ")" +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    CountDTO findCountOrderStatusCompleted(String completed);



    //đếm SL đơn hàng theo trang trạng thái đang  chờ xử lí
    @Query("SELECT NEW com.cg.domain.dto.CountDTO ( " +
            "COUNT (o.id) " +
            ")" +
            "FROM Order AS o " +
            "WHERE o.orderStatus.titleEn = ?1 "
    )
    CountDTO findCountOrderStatusCancel(String cancel);

    //Tien
    @Query("SELECT NEW com.cg.domain.dto.OrderDTO (" +
                "o.id, " +
                "o.updateAt, " +
                "o.customer, " +
                "o.orderStatus, " +
                "o.grandTotal" +
            ") " +
            "FROM Order AS o " +
            "ORDER BY o.id DESC"
    )
    List<OrderDTO> findAllOrdersDTO();

    //Vinh viết stored procedure tất cả dữ liệu cần thiết của order
    //Hiển thị các thông tin ở phần Tổng quan(Tổng tiền theo ngày, theo tháng, count đơn hàng bằng stored procedure)
    @Query(value = "CALL sp_select_all_order();", nativeQuery = true)
    List<IOrderDTO> findAllOrderDTOData();




    @Query(value = "CALL sp_order_total_today();", nativeQuery = true)
    List<ITotalAnalytics> orderTotalToday();

    @Query(value = "CALL sp_order_total_week();", nativeQuery = true)
    List<ITotalAnalytics> orderTotalWeek();

    @Query(value = "CALL sp_order_total_month();", nativeQuery = true)
    List<ITotalAnalytics> orderTotalMonth();
}
