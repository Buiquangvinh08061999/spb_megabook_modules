package com.cg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GrandTotalPendingDTO {

    private BigDecimal grandTotal; //tổng tiền theo đơn hàng theo trạng thái đang chờ xử lí


}
