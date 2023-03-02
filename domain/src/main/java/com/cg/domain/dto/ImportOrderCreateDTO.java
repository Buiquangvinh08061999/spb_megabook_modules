package com.cg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImportOrderCreateDTO {

    @NotNull(message = "Vui lòng chọn nhà cung cấp")
    Long supplierId;

    @NotNull(message = "Vui lòng chọn kho nhập")
    Long warehouseId;

    @NotNull(message = "Vui lòng chọn nhân viên")
    String userId;

    @NotEmpty(message = "Vui lòng chọn ít nhất 1 mặt hàng")
    List<OrderItemsCreateDTO> orderItems;
}
