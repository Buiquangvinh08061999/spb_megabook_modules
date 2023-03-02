package com.cg.domain.entity;

import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "import_order_items")

public class ImportOrderItem extends BaseEntities{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "product_sku")
    private String productSku;

    @Column(name = "item_quantity") // số lượng đặt
    private int itemQuantity;

    private int available; //Tồn kho

    @Column(name = "item_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal itemPrice;

    @Column(name = "total_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal totalPrice;


    @ManyToOne
    @JoinColumn(name = "import_order_id")
    private ImportOrder importOrder;


    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public ImportOrderItemDTO toImportOrderItemDTO() {
        return new ImportOrderItemDTO()
                .setId(id)
                .setProductTitle(productTitle)
                .setProductSku(productSku)
                .setItemQuantity(itemQuantity)
                .setAvailable(available)
                .setItemPrice(itemPrice)
                .setTotalPrice(totalPrice)
                .setImportOrder(importOrder.toImportOrderDTO())
                .setItem(item.toItemDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());
    }


}
