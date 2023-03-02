package com.cg.domain.dto;

import com.cg.domain.entity.Customer;
import com.cg.domain.entity.Order;
import com.cg.domain.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private Long id;
    private BigDecimal grandTotal;
    private String describe;

    @Valid
    private CustomerDTO customer;

    @Valid
    private OrderStatusDTO orderStatus;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;


    public Order toOrder() {
        return (Order) new Order()
                .setId(id)
                .setGrandTotal(grandTotal)
                .setDescribe(describe)
                .setCustomer(customer.toCustomer())
                .setOrderStatus(orderStatus.toOrderStatus())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }
    //1.Phần add order, kiểm tra order theo customer id truyền vào và trạng thái mặc định là PENDING, phần api bên kia nếu khác trạng thái tạo đơn hàng mới
    public OrderDTO(Long id, BigDecimal grandTotal, String describe, Date createAt, String createBy, Date updateAt, String updateBy , Customer customer, OrderStatus orderStatus) {
        this.id = id;
        this.grandTotal = grandTotal;
        this.describe = describe;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.customer = customer.toCustomerDTO();
        this.orderStatus = orderStatus.toOrderStatusDTO();
    }

    public OrderDTO(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    //Tien
    public OrderDTO(Long id, Date createAt, Customer customer, OrderStatus orderStatus, BigDecimal grandTotal) {
        this.id = id;
        this.createAt = createAt;
        this.customer = customer.toCustomerDTO();
        this.orderStatus = orderStatus.toOrderStatusDTO();
        this.grandTotal = grandTotal;
    }
}
