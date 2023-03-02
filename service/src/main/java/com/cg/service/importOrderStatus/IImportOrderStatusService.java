package com.cg.service.importOrderStatus;

import com.cg.domain.dto.ImportOrderStatusDTO;
import com.cg.domain.entity.ImportOrderStatus;
import com.cg.domain.entity.OrderStatus;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IImportOrderStatusService extends IGeneralService<ImportOrderStatus> {

    List<ImportOrderStatusDTO> findAllImportOrderStatusDTO();

    List<ImportOrderStatusDTO> findByTitleEnCOMPLETEDAndCANCEL(String pending);

}
