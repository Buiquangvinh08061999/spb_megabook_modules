package com.cg.domain.dto;

import com.cg.domain.entity.ImportOrder;
import com.cg.domain.entity.ImportOrderItem;
import com.cg.domain.entity.Item;
import com.cg.domain.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImportOrderItemDTO {

    private Long id;
    private String productTitle;
    private String productSku;
    private int itemQuantity;
    private int available; //Tồn kho
    private BigDecimal itemPrice;
    private BigDecimal totalPrice;

    @Valid
    private ImportOrderDTO importOrder;

    @Valid
    private ItemDTO item;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;


    public ImportOrderItem toImportOrderItem() {
        return (ImportOrderItem) new ImportOrderItem()
                .setId(id)
                .setProductTitle(productTitle)
                .setProductSku(productSku)
                .setItemQuantity(itemQuantity)
                .setAvailable(available)
                .setItemPrice(itemPrice)
                .setTotalPrice(totalPrice)
                .setImportOrder(importOrder.toImportOrder())
                .setItem(item.toItem())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);

    }

    public ImportOrderItemDTO(Long id, String productTitle, String productSku, int itemQuantity, BigDecimal itemPrice, BigDecimal totalPrice, Date createAt, String createBy, Date updateAt, String updateBy, Item item ,ImportOrder importOrder) {
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
        this.importOrder = importOrder.toImportOrderDTO();
    }

}
