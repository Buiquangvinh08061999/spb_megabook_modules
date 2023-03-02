package com.cg.domain.dto;

public interface ISumQuantityItemsDTO {

    int getSumQuantity(); //Tổng đơn hàng

    int getSumAvailable(); //Tổng đơn hàng còn tồn ở kho

    int getSumSold(); //Tổng đơn hàng đã bán

}
