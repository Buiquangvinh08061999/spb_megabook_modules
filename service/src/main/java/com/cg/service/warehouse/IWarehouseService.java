package com.cg.service.warehouse;

import com.cg.domain.dto.WarehouseDTO;
import com.cg.domain.entity.Warehouse;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IWarehouseService extends IGeneralService<Warehouse> {
    List<WarehouseDTO> findAllWarehouseDTOByDeletedIsFalse();

    //2.Kiểm tra điều kiện create
    Boolean existsByTitle(String title);
}
