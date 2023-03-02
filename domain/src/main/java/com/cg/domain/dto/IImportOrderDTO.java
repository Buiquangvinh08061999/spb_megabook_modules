package com.cg.domain.dto;

import java.math.BigDecimal;

public interface IImportOrderDTO {

    int getCount(); //đếm SL dùng chung 2 trạng thái

    BigDecimal getSumQuantity();    // tổng SL dùng chung 2 trạng thái

    BigDecimal getSum();   //tổng tiền dùng chung 2 trạng thái

}
