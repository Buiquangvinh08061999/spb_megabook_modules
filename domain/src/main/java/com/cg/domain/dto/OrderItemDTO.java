package com.cg.domain.dto;

import com.cg.domain.entity.Item;
import com.cg.domain.entity.Order;
import com.cg.domain.entity.OrderItem;
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
public class OrderItemDTO {

    private Long id;
    private String productTitle;
    private String productSku;
    private int itemQuantity;
    private BigDecimal itemPrice;
    private BigDecimal totalPrice;

    @Valid
    private OrderDTO order;

    @Valid
    private ItemDTO item;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;

    public OrderItem toOrderItem() {
        return (OrderItem) new OrderItem()
                .setId(id)
                .setProductTitle(productTitle)
                .setProductSku(productSku)
                .setItemQuantity(itemQuantity)
                .setItemPrice(itemPrice)
                .setTotalPrice(totalPrice)
                .setOrder(order.toOrder())
                .setItem(item.toItem())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }

    public OrderItemDTO(Long id, String productTitle, String productSku, int itemQuantity, BigDecimal itemPrice, BigDecimal totalPrice, Date createAt, String createBy, Date updateAt, String updateBy, Item item , Order order) {
        this.id = id;
        this.productTitle = productTitle;
        this.productSku = productSku;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.item = item.toItemDTO();
        this.order = order.toOrderDTO();
    }
}
