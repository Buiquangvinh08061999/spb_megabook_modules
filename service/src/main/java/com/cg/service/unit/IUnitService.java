package com.cg.service.unit;

import com.cg.domain.dto.UnitDTO;
import com.cg.domain.entity.Unit;
import com.cg.service.IGeneralService;

import java.util.List;


public interface IUnitService extends IGeneralService<Unit> {
    //1.Hiển thị tất cả danh sách Unit ra
    List<UnitDTO> findAllUnitDTO();
}
