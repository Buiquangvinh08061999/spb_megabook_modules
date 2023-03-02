package com.cg.api;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.entity.Customer;
import com.cg.domain.entity.Supplier;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.service.supplier.ISupplierService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ISupplierService supplierService;

    //Hiển thị tất cả danh sách nhà cung cấp
    @GetMapping()
    public ResponseEntity<?> findAllSupplierDTO() {
        List<SupplierDTO> supplierDTOList = supplierService.findAllSupplierDTOByDeletedIsFalse();

        if (supplierDTOList.isEmpty()) {
            throw new DataInputException("Nhà cung cấp rỗng, vui lòng kiểm tra lại!");
        }

        return new ResponseEntity<>(supplierDTOList, HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody SupplierDTO supplierDTO, BindingResult bindingResult) {


        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        supplierDTO.setId(0L);
        supplierDTO.getLocationRegion().setId(0L);

        Boolean existsByTitle = supplierService.existsByTitle(supplierDTO.getTitle());
        if (existsByTitle) {
            throw new EmailExistsException("Tên nhà cung cấp đã tồn tại vui lòng kiểm tra lại");
        }


        try {
            Supplier supplier = supplierDTO.toSupplier();

            Supplier newSupplier = supplierService.save(supplier); //vào phương thức save để xử lí tiếp , lưu địa chỉ id vào customer(location_id)

            return new ResponseEntity<>(newSupplier.toSupplierDTO(), HttpStatus.CREATED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }
}
