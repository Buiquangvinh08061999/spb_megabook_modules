package com.cg.domain.entity;

import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.dto.ItemDTO;
import com.cg.domain.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal costPrice;

    @Column(name = "total_cost_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal totalCostPrice;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    private int quantity;

    private int sold;

    private int available;

    private int defective;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @OneToMany(mappedBy = "item")
    private Set<OrderItem> orderItems;


    @OneToMany(mappedBy = "item") // mới thêm vào
    private Set<ImportOrderItem> importOrderItems;


    public ItemDTO toItemDTO() {
        return new ItemDTO()
                .setId(id)
                .setCostPrice(costPrice)
                .setTotalCostPrice(totalCostPrice)
                .setPrice(price)
                .setQuantity(quantity)
                .setSold(sold)
                .setAvailable(available)
                .setDefective(defective)
                .setProduct(product.toProductDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());
    }


    //add order
    public OrderItemDTO toOrderItemDTO(BigDecimal totalPrice) {
        return new OrderItemDTO()
                .setId(id) //lấy theo id item(1)
                .setProductTitle(product.getTitle()) //tên sản phẩm
                .setProductSku(product.getSku())
                .setItemPrice(price) //bán ra, lưu giá bán
                .setTotalPrice(totalPrice);

    }


    //add ImportOrder
    public ImportOrderItemDTO toImportOrderItemDTO(BigDecimal totalPrice){
        return new ImportOrderItemDTO()
                .setId(id)
                .setProductTitle(product.getTitle())
                .setProductSku(product.getSku())
                .setItemPrice(costPrice) //mua vào, lưu giá nhập
                .setTotalPrice(totalPrice);
    }

}
