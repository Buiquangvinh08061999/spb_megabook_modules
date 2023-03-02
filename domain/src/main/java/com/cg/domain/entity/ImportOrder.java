package com.cg.domain.entity;

import com.cg.domain.dto.ImportOrderDTO;
import com.cg.domain.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "import_orders")
public class ImportOrder extends BaseEntities{ //quản lí tồn kho ( tạo đơn đặt hàng)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int grandQuantity; //Tổng số lượng

    private String note; // ghi chú;

    @Digits(integer = 12, fraction = 0)
    @Column(name = "grand_total")
    private BigDecimal grandTotal; //Tổng tiền cần trả

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;


    @ManyToOne
    @JoinColumn(name = "import_order_status_id")
    private ImportOrderStatus importOrderStatus;


    @OneToMany(mappedBy = "importOrder")
    private Set<ImportOrderItem> importOrderItems;


    public ImportOrderDTO toImportOrderDTO() {
        return new ImportOrderDTO()
                .setId(id)
                .setGrandQuantity(grandQuantity)
                .setNote(note)
                .setGrandTotal(grandTotal)
                .setUser(user.toUserDTO())
                .setSupplier(supplier.toSupplierDTO())
                .setWarehouse(warehouse.toWarehouseDTO())
                .setImportOrderStatus(importOrderStatus.toImportOrderStatusDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());

    }
}
