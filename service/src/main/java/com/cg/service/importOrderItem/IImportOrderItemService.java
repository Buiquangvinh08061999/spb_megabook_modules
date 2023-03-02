package com.cg.service.importOrderItem;

import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.entity.ImportOrderItem;
import com.cg.domain.entity.OrderItem;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IImportOrderItemService extends IGeneralService<ImportOrderItem> {

    List<ImportOrderItemDTO> findAllByOrderId(Long importOrderId);
}
