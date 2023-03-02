package com.cg.api;

import com.cg.domain.dto.*;
import com.cg.domain.entity.*;
import com.cg.domain.enums.EImportOrderStatus;
import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.importOrder.IImportOrderService;
import com.cg.service.importOrderItem.IImportOrderItemService;
import com.cg.service.importOrderStatus.IImportOrderStatusService;
import com.cg.service.item.IItemService;
import com.cg.service.supplier.ISupplierService;
import com.cg.service.user.IUserService;
import com.cg.service.warehouse.IWarehouseService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/import-orders")
public class ImportOrderAPI {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IItemService iItemService;


    @Autowired
    private IImportOrderService importOrderService;

    @Autowired
    private IImportOrderItemService importOrderItemService;

    @Autowired
    private IImportOrderStatusService importOrderStatusService;


    //1.add
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@Validated @RequestBody ImportOrderCreateDTO importOrderCreateDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Supplier> supplierOptional = supplierService.findById(importOrderCreateDTO.getSupplierId());
        if (!supplierOptional.isPresent()) {
            throw new DataInputException("Nhà cung cấp không hợp lệ");
        }

        Optional<Warehouse> warehouseOptional = warehouseService.findById(importOrderCreateDTO.getWarehouseId());
        if (!warehouseOptional.isPresent()) {
            throw new DataInputException("Kho Hàng không hợp lệ");
        }

        Optional<User> userOptional = userService.findById(importOrderCreateDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new DataInputException("Mã user(admin-staff) không hợp lệ");
        }

        Supplier supplier = supplierOptional.get();
        Warehouse warehouse = warehouseOptional.get();
        User user = userOptional.get();


        List<OrderItemsCreateDTO> itemsList = importOrderCreateDTO.getOrderItems();  //Đây là 1 object bao gồm { idItem truyền vào (1), Số lượng đặt hàng (3))

        List<ImportOrderItemDTO> importOrderItemDTOList = new ArrayList<>();


        for (OrderItemsCreateDTO item : itemsList){

            Optional<Item> itemOptional = iItemService.findById(item.getId());
            if(!itemOptional.isPresent()){
                throw new DataInputException("Mã sản phẩm không tồn tại");
            }

            int quantity = item.getQuantity();
            BigDecimal price = itemOptional.get().getCostPrice(); // đây là giá nhập

            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

            ImportOrderItemDTO importOrderItemDTO = itemOptional.get().toImportOrderItemDTO(totalPrice);

            importOrderItemDTO.setItemQuantity(quantity); //lưu số lượng mới mua vào, giờ Sl (ví dụ = 2)
            importOrderItemDTO.setItem(itemOptional.get().toItemDTO()); //lưu đối tượng item


            importOrderItemDTOList.add(importOrderItemDTO);
        }


        importOrderService.createImportOrder(importOrderItemDTOList, supplier, warehouse, user); //truyền list mới được tạo vào

        Map<String, Object> result = new HashMap<>();
        String success;

        success = "thành công";
        result.put("success", success);


        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    //Hiển thị tất cả danh sách list ImportOrder
    @GetMapping("/list")
    public ResponseEntity<?> findAllImportOrderDTO() {
        List<ImportOrderDTO> importOrderDTOList = importOrderService.findAllImportOrderDTO();

        if (importOrderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách ImportOrder rỗng");
        }
        return new ResponseEntity<>(importOrderDTOList, HttpStatus.OK);
    }

    @GetMapping("/list-status-pending")
    public ResponseEntity<?> findAllImportOrderDTOByPENDING() {
        List<ImportOrderDTO> importOrderDTOList = importOrderService.findByImportOrderDTOByStatusPENDING(EImportOrderStatus.PENDING.getValue());

        if (importOrderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách ImportOrder  trạng thái chờ nhập rỗng");
        }
        return new ResponseEntity<>(importOrderDTOList, HttpStatus.OK);
    }


    @GetMapping("/list-status-completed")
    public ResponseEntity<?> findAllImportOrderDTOByCOMPLETED() {
        List<ImportOrderDTO> importOrderDTOList = importOrderService.findByImportOrderDTOByStatusCOMPLETED(EImportOrderStatus.COMPLETED.getValue());

        if (importOrderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách ImportOrder trạng thái hoàn thành rỗng");
        }
        return new ResponseEntity<>(importOrderDTOList, HttpStatus.OK);
    }


    @GetMapping("/list-status-cancel")
    public ResponseEntity<?> findAllImportOrderDTOByCancel() {
        List<ImportOrderDTO> importOrderDTOList = importOrderService.findByImportOrderDTOByStatusCancel(EImportOrderStatus.CANCEL.getValue());

        if (importOrderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách ImportOrder trạng thái hoàn thành rỗng");
        }
        return new ResponseEntity<>(importOrderDTOList, HttpStatus.OK);
    }





    //Tìm thông tin của lẻ của ImportOrderId
    @GetMapping("/{id}")
    public ResponseEntity<?> displayImportOrderById(@PathVariable long id) {
        Optional<ImportOrder> importOrderOptional = importOrderService.findById(id);

        if (!importOrderOptional.isPresent()) {
            throw new ResourceNotFoundException("Mã Id ImportOrder không tồn tại");
        }
        return new ResponseEntity<>(importOrderOptional.get().toImportOrderDTO(), HttpStatus.OK);
    }


    @PutMapping("/update-status/{importOrderId}") //truyển trạng thái, đưa importId, và trạng thái vào data json, khi imporder hoàn thành =2 , thì ta cộng số lượng và tiền vào bảng items xử lí bên service
    public ResponseEntity<?> doUpdateStatus(@PathVariable long importOrderId, @RequestBody ImportOrderStatusDTO importOrderStatusDTO) {

        Optional<ImportOrder> importOrderOptional = importOrderService.findById(importOrderId);
        if(!importOrderOptional.isPresent()){
            throw new ResourceNotFoundException("Mã Id ImportOrder không tồn tại");
        }

        Optional<ImportOrderStatus> importOrderStatusOptional = importOrderStatusService.findById(importOrderStatusDTO.getId());
        if(!importOrderStatusOptional.isPresent()){
            throw new ResourceNotFoundException("Không tìm thấy trạng thái này");
        }

        ImportOrder importOrder = importOrderService.doChangeStatusOrder(importOrderOptional.get(), importOrderStatusOptional.get());


        return new ResponseEntity<>(importOrder.toImportOrderDTO(),  HttpStatus.OK);
    }


    @GetMapping("/total-today")
    public ResponseEntity<?> importTotalToday() {
        List<ITotalAnalytics> iTotalAnalyticsList = importOrderService.importTotalToday();
        return new ResponseEntity<>(iTotalAnalyticsList, HttpStatus.OK);
    }

    //Vinh viết sp,importorder
    @GetMapping("/count-sum-import-order-pending")
    public ResponseEntity<List<?>> findAllImportOrderDTOCountAndSumPending() {
        List<IImportOrderDTO> importOrderDTOList = importOrderService.findAllImportOrderDTOCountAndSumPending();

        return new ResponseEntity<>(importOrderDTOList, HttpStatus.OK);

    }

    @GetMapping("/count-sum-import-order-completed")
    public ResponseEntity<List<?>> findAllImportOrderDTOCountAndSumCompleted() {
        List<IImportOrderDTO> importOrderDTOList = importOrderService.findAllImportOrderDTOCountAndSumCompleted();

        return new ResponseEntity<>(importOrderDTOList, HttpStatus.OK);

    }


    @GetMapping("/total-week")
    public ResponseEntity<?> importTotalWeek() {
        List<ITotalAnalytics> iTotalAnalyticsList = importOrderService.importTotalWeek();
        return new ResponseEntity<>(iTotalAnalyticsList, HttpStatus.OK);
    }

    @GetMapping("/total-month")
    public ResponseEntity<?> importTotalMonth() {
        List<ITotalAnalytics> iTotalAnalyticsList = importOrderService.importTotalMonth();
        return new ResponseEntity<>(iTotalAnalyticsList, HttpStatus.OK);
    }
}
