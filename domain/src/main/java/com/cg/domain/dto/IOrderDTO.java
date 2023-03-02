package com.cg.domain.dto;

import java.math.BigDecimal;

public interface IOrderDTO {

    BigDecimal getGrandTotalDay();

    BigDecimal getGrandTotalMonth();

    int getCountALL();

    int getCountStatusPending();

    int getCountStatusCompleted();

    int getCountStatusCancel();

}
