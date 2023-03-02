package com.cg.domain.dto;

import com.cg.domain.entity.*;
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
public class ImportOrderDTO {

    private Long id;
    private int grandQuantity; //Tổng số lượng
    private String note; // ghi chú;
    private BigDecimal grandTotal;

    @Valid
    private UserDTO user;

    @Valid
    private SupplierDTO supplier;

    @Valid
    private WarehouseDTO warehouse;

    @Valid
    private ImportOrderStatusDTO importOrderStatus;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;

    public ImportOrder toImportOrder() {
        return (ImportOrder) new ImportOrder()
                .setId(id)
                .setGrandQuantity(grandQuantity)
                .setNote(note)
                .setGrandTotal(grandTotal)
                .setUser(user.toUser())
                .setSupplier(supplier.toSupplier())
                .setWarehouse(warehouse.toWarehouse())
                .setImportOrderStatus(importOrderStatus.toImportOrderStatus())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }
    //1.Phần add ImportOrder
    public ImportOrderDTO(Long id, int grandQuantity, String note, BigDecimal grandTotal, Date createAt, String createBy, Date updateAt, String updateBy, User user, Supplier supplier, Warehouse warehouse, ImportOrderStatus importOrderStatus) {
        this.id = id;
        this.grandQuantity = grandQuantity;
        this.note = note;
        this.grandTotal = grandTotal;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.user = user.toUserDTO();
        this.supplier = supplier.toSupplierDTO();
        this.warehouse = warehouse.toWarehouseDTO();
        this.importOrderStatus = importOrderStatus.toImportOrderStatusDTO();
    }
}
