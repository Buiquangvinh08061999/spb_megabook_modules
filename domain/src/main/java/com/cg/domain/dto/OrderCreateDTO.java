package com.cg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderCreateDTO {

    @NotNull(message = "Vui lòng chọn khách hàng")
    Long customerId;

    @NotEmpty(message = "Vui lòng chọn ít nhất 1 sản phẩm")
    List<OrderItemsCreateDTO> orderItems;
}

