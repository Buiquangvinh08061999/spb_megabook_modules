package com.cg.domain.dto;

import com.cg.domain.entity.Item;
import com.cg.domain.entity.Product;
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
public class ItemDTO {

    private Long id;
    private BigDecimal costPrice;
    private BigDecimal totalCostPrice;
    private BigDecimal price;
    private int quantity;
    private int sold;
    private int available;
    private int defective;

    @Valid
    private ProductDTO product;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;


    public Item toItem() {
        return (Item) new Item()
                .setId(id)
                .setCostPrice(costPrice)
                .setTotalCostPrice(totalCostPrice)
                .setPrice(price)
                .setQuantity(quantity)
                .setSold(sold)
                .setAvailable(available)
                .setDefective(defective)
                .setProduct(product.toProduct())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }



    //1.Hiển thị tất cả danh sách Item ra;
    public ItemDTO(Long id, BigDecimal costPrice, BigDecimal totalCostPrice, BigDecimal price, int quantity, int sold, int available, int defective, Date createAt, Date updateAt, Product product) {
        this.id = id;
        this.costPrice = costPrice;
        this.totalCostPrice = totalCostPrice;
        this.price = price;
        this.quantity = quantity;
        this.sold = sold;
        this.available = available;
        this.defective = defective;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.product = product.toProductDTO();
    }

    //2.
    public ItemDTO(Long id, BigDecimal costPrice, BigDecimal totalCostPrice, BigDecimal price, int quantity, int sold, int available, int defective, Product product) {
        this.id = id;
        this.costPrice = costPrice;
        this.totalCostPrice = totalCostPrice;
        this.price = price;
        this.quantity = quantity;
        this.sold = sold;
        this.available = available;
        this.defective = defective;
        this.product = product.toProductDTO();
    }


    public ItemDTO(Long id, BigDecimal price, int available, Product product) {
        this.id = id;
        this.price = price;
        this.available = available;
        this.product = product.toProductInfoDTO();
    }

    public ItemDTO(Long id, BigDecimal costPrice, Product product) {
        this.id = id;
        this.costPrice = costPrice;
        this.product = product.toProductInfoDTO();
    }

}
