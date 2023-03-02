package com.cg.api;

import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.dto.WarehouseDTO;
import com.cg.domain.entity.Supplier;
import com.cg.domain.entity.Warehouse;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.supplier.ISupplierService;
import com.cg.service.warehouse.IWarehouseService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IWarehouseService warehouseService;


    //Hiển thị tất cả danh sách kho hàng
    @GetMapping()
    public ResponseEntity<?> findAllWarehouseDTO() {
        List<WarehouseDTO> warehouseDTOList = warehouseService.findAllWarehouseDTOByDeletedIsFalse();

        if (warehouseDTOList.isEmpty()) {
            throw new DataInputException("Kho hàng rỗng, vui lòng kiểm tra lại!");
        }

        return new ResponseEntity<>(warehouseDTOList, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody WarehouseDTO warehouseDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        warehouseDTO.setId(0L);
        warehouseDTO.getLocationRegion().setId(0L);

        Boolean existsByTitle = warehouseService.existsByTitle(warehouseDTO.getTitle());
        if (existsByTitle) {
            throw new EmailExistsException("Tên kho đã tồn tại vui lòng kiểm tra lại");
        }

        try {
            Warehouse warehouse = warehouseDTO.toWarehouse();

            Warehouse newWarehouse = warehouseService.save(warehouse); //vào phương thức save để xử lí tiếp , lưu địa chỉ id vào customer(location_id)

            return new ResponseEntity<>(newWarehouse.toWarehouseDTO(), HttpStatus.CREATED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }


}
