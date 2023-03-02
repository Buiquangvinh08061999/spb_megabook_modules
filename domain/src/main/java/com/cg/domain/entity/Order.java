package com.cg.domain.entity;

import com.cg.domain.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

//Tien
//@NamedNativeQuery(
//        name = "sp_week_total_analytics",
//        query = "call megabook.sp_week_total_analytics();",
//        resultSetMapping = "result_sp_week_total_analytics"
//)
//@SqlResultSetMapping(
//        name = "result_sp_week_total_analytics",
//        classes = @ConstructorResult(
//                targetClass = Chart.class,
//                columns = {
//                        @ColumnResult(name = "time_ipt", type = Date.class),
//                        @ColumnResult(name = "total_ipt", type = BigDecimal.class),
//                        @ColumnResult(name = "exp_time", type = Date.class),
//                        @ColumnResult(name = "grand_total", type = BigDecimal.class),
//
//                }
//        )
//)
//
//@NamedNativeQuery(
//        name = "sp_total_today",
//        query = "call megabook.sp_total_today();",
//        resultSetMapping = "result_chartTotalToday"
//)
//@SqlResultSetMapping(
//        name = "result_chartTotalToday",
//        classes = @ConstructorResult(
//                targetClass = Chart.class,
//                columns = {
//                        @ColumnResult(name = "total", type = BigDecimal.class),
//                        @ColumnResult(name = "times", type = Date.class)
//                }
//        )
//)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grand_total", precision = 12, scale = 0, nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "describe_order")
    private String describe;

    @Column(name = "other_recipient", columnDefinition = "boolean default false")
    private boolean otherRecipient;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @OneToOne(targetEntity = LocationRegionRecipient.class, mappedBy = "order", fetch = FetchType.EAGER)
    private LocationRegionRecipient locationRegionRecipient;


    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;


    public OrderDTO toOrderDTO() {
        return new OrderDTO()
                .setId(id)
                .setGrandTotal(grandTotal)
                .setDescribe(describe)
                .setCustomer(customer.toCustomerDTO())
                .setOrderStatus(orderStatus.toOrderStatusDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());
    }
}
