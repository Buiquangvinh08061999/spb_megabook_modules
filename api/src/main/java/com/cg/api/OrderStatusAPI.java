package com.cg.api;

import com.cg.domain.dto.OrderStatusDTO;
import com.cg.domain.enums.EOrderStatus;
import com.cg.exception.DataInputException;
import com.cg.service.orderStatus.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-status")
public class OrderStatusAPI {

    @Autowired
    private IOrderStatusService orderStatusService;


    @GetMapping()
    public ResponseEntity<?> findAllOrderStatusDTO(){
        List<OrderStatusDTO> orderStatusList = orderStatusService.findAllOrderStatusDTO();

        if(orderStatusList.isEmpty()){
            throw new DataInputException("Danh sách order-status rỗng, không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderStatusList, HttpStatus.OK);
    }

    //Hiển thị hai trạng thái 2(COMPLETED-Hoàn thành) và 3(CANCEL)
    @GetMapping("/list-title-completed-and-cancel")
    public ResponseEntity<?> findByTitleEnCOMPLETEDAndCANCEL(){
        List<OrderStatusDTO> orderStatusList = orderStatusService.findByTitleEnCOMPLETEDAndCANCEL(EOrderStatus.PENDING.getValue());

        if(orderStatusList.isEmpty()){
            throw new DataInputException("Danh sách order-status rỗng , không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderStatusList,HttpStatus.OK);
    }



}
