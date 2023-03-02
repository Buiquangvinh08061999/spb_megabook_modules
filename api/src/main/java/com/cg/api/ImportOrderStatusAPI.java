package com.cg.api;

import com.cg.domain.dto.ImportOrderStatusDTO;
import com.cg.domain.dto.OrderStatusDTO;
import com.cg.domain.enums.EImportOrderStatus;
import com.cg.domain.enums.EOrderStatus;
import com.cg.exception.DataInputException;
import com.cg.service.importOrderStatus.IImportOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/import-order-status")
public class ImportOrderStatusAPI {

    @Autowired
    private IImportOrderStatusService importOrderStatusService;

    @GetMapping()
    public ResponseEntity<?> findAllOrderStatusDTO(){
        List<ImportOrderStatusDTO> orderStatusList = importOrderStatusService.findAllImportOrderStatusDTO();

        if(orderStatusList.isEmpty()){
            throw new DataInputException("Danh sách import-order-status rỗng, không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderStatusList, HttpStatus.OK);
    }


    //Hiển thị hai trạng thái 2(COMPLETED-Hoàn thành) và 3(CANCEL)
    @GetMapping("/list-title-completed-and-cancel")
    public ResponseEntity<?> findByTitleEnCOMPLETEDAndCANCEL(){
        List<ImportOrderStatusDTO> orderStatusList = importOrderStatusService.findByTitleEnCOMPLETEDAndCANCEL(EImportOrderStatus.PENDING.getValue());

        if(orderStatusList.isEmpty()){
            throw new DataInputException("Danh sách import-order-status rỗng , không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderStatusList,HttpStatus.OK);
    }
}
