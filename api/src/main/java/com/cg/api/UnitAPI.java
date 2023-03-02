package com.cg.api;


import com.cg.domain.dto.UnitDTO;
import com.cg.exception.DataInputException;
import com.cg.service.unit.IUnitService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitAPI {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUnitService unitService;
    //Lấy tất cả danh mục của unit ra;

    @GetMapping()
    public ResponseEntity<?> findAllUnitDTO() {
        List<UnitDTO> unitDTOList = unitService.findAllUnitDTO();

        if (unitDTOList.isEmpty()) {
            throw new DataInputException("Đơn rỗng không có dữ liệu, vui lòng kiểm tra lại!");
        }

        return new ResponseEntity<>(unitDTOList, HttpStatus.OK);
    }
}

