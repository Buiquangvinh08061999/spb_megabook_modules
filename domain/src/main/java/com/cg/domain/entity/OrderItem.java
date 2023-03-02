package com.cg.domain.entity;

import com.cg.domain.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "product_sku")
    private String productSku;

    @Column(name = "item_quantity")
    private int itemQuantity;

    @Column(name = "item_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal itemPrice;

    @Column(name = "total_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;


    public OrderItemDTO toOrderItemDTO() {
        return new OrderItemDTO()
                .setId(id)
                .setProductTitle(productTitle)
                .setProductSku(productSku)
                .setItemQuantity(itemQuantity)
                .setItemPrice(itemPrice)
                .setTotalPrice(totalPrice)
                .setOrder(order.toOrderDTO())
                .setItem(item.toItemDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());
    }
}
