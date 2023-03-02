package com.cg.domain.dto;

import com.cg.domain.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderStatusDTO {

    private Long id;
    private String titleEn;
    private String titleVi;


    public OrderStatus toOrderStatus() {
        return new OrderStatus()
                .setId(id)
                .setTitleEn(titleEn)
                .setTitleVi(titleVi);
    }
}
