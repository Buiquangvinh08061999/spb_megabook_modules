package com.cg.api;

import com.cg.domain.dto.*;
import com.cg.domain.entity.*;
import com.cg.domain.enums.EOrderStatus;
import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.customer.ICustomerService;
import com.cg.service.item.IItemService;
import com.cg.service.order.IOrderService;
import com.cg.service.orderItem.IOrderItemService;
import com.cg.service.orderStatus.IOrderStatusService;
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
@RequestMapping("/api/orders")
public class OrderAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IItemService iItemService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IOrderStatusService orderStatusService;


    @GetMapping
    public ResponseEntity<?> findAllOrdersDTO() {
        List<OrderDTO> orderDTOList = orderService.findAllOrdersDTO();

        if (orderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách đơn hàng trống");
        }

        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@Validated @RequestBody OrderCreateDTO orderCreateDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Customer> customerOptional = customerService.findById(orderCreateDTO.getCustomerId()); //Lấy được id người đặt
        if (!customerOptional.isPresent()) {
            throw new DataInputException("Khách hàng không hợp lệ");
        }
        Customer customer = customerOptional.get();

        List<OrderItemsCreateDTO> itemsList = orderCreateDTO.getOrderItems();   // Đây là 1 object bao gồm { idItem truyền vào (1), Số lượng đặt hàng (3))

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>(); //dữ liệu của orderItem rỗng, size=0,

        for (OrderItemsCreateDTO item : itemsList) {  //duyệt qua object để lấy dữ liệu ra, lấy được Id 1, số lượng mình đặt(số lượng 2)

            Optional<Item> itemOptional = iItemService.findById(item.getId());      //duyệt qua 1 mảng IdItem truyền vào(lấy được số giá, số lượng của nó ví dụ 10)

            if (!itemOptional.isPresent()) {
                throw new DataInputException("Mã sản phẩm không tồn tại");
            }

            BigDecimal price = itemOptional.get().getPrice(); //giá được lấy ra từ itemOptional theo id
            int quantity = item.getQuantity();  //số lượng đặt(vd:2)

            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity)); // tổng tiền giá * sl(2), truyền vào orderItem

            OrderItemDTO orderItemDTO = itemOptional.get().toOrderItemDTO(totalPrice);    //Đối tượng OrderItem mới tăng theo vòng for , truyền totalPrice tính vào

            orderItemDTO.setItemQuantity(quantity);                      //lưu số lượng mình mua vào
            orderItemDTO.setItem(itemOptional.get().toItemDTO());        //lưu itemId vào


            orderItemDTOList.add(orderItemDTO);  //size == 0, hết vòng for size == 4, đã có dữ liệu tiền số lương của mỗi thằng,  truyền vào  service để xử lí
        }

        //ta chưa bắn ra lỗi, cứ cho người dùng vào,add số lượng tùy ý muốn mua vào, rồi mới xử lí, orderItemDTOList đã có dữ liệu số lượng được đặt,VD 2

        Map<String, List<OrderCreateErrorDTO>> result = new HashMap<>();
        List<OrderCreateErrorDTO> orderCreateErrorDTOList = new ArrayList<>();
        Long errorId = 0L;
        boolean hasError = false;

        for (OrderItemDTO item : orderItemDTOList) {     //Id orderItemDTOList được gán theo IdItem ta truyền vào,hiện tại nó đã có Id,dữ liệu mới, SL mới(2) giờ ta đem ra so sánh nó vs số lượng ItemId hiện tại ra

            Optional<Item> itemOptional = iItemService.findById(item.getId()); //1 lấy item đó ra để xem SL hiển thị

            if (item.getItemQuantity() > itemOptional.get().getAvailable()) { //SL đặt(2) lớn SL tồn kho thì báo lỗi
                hasError = true;

                OrderCreateErrorDTO orderCreateErrorDTO = new OrderCreateErrorDTO();
                orderCreateErrorDTO.setId(errorId);
                String message = "Sản phẩm " + item.getProductTitle() + " chỉ còn " + itemOptional.get().getAvailable() + " sản phẩm";
                orderCreateErrorDTO.setMessage(message);

                orderCreateErrorDTOList.add(orderCreateErrorDTO); //đẩy lỗi vào listErrors
                errorId++;

            }

            if (item.getItemQuantity() < 1) {
                hasError = true;

                OrderCreateErrorDTO orderCreateErrorDTO = new OrderCreateErrorDTO();
                orderCreateErrorDTO.setId(errorId);
                String message = "Số lượng sản phẩm " + item.getProductTitle() + " phải là số nguyên và ít nhất là 1";
                orderCreateErrorDTO.setMessage(message);

                orderCreateErrorDTOList.add(orderCreateErrorDTO);
                errorId++;
            }
        }

        if (hasError) {
            result.put("errors", orderCreateErrorDTOList); // hiển thị lỗi ra
            System.out.println(result);

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);  // trả về tập lỗi ra
        }

        try {
            orderService.createOrder(orderItemDTOList, customer);   //Truyền list OrderItems vào, và customer Id khác hàng vào

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //2.Hiển thị tất cả order theo trạng thái PENDING
    @GetMapping("/list-status-pending")
    public ResponseEntity<?> findAllOrderDTOByPENDING() {
        List<OrderDTO> orderDTOList = orderService.findAllOrderDTOByOrderStatusPENDING(EOrderStatus.PENDING.getValue());

        if (orderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của order theo trạng thái PENDING rỗng, không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    //.Hiển thị tất cả order theo trạng thái COMPLETED
    @GetMapping("/list-status-completed")
    public ResponseEntity<?> findAllOrderDTOByCOMPLETED() {
        List<OrderDTO> orderDTOList = orderService.findAllOrderDTOByOrderStatusCOMPLETED(EOrderStatus.COMPLETED.getValue());

        if (orderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của order theo trạng thái COMPLETED rỗng, không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    //3.Hiển thị tất cả order theo trạng thái CANCEL
    @GetMapping("/list-status-cancel")
    public ResponseEntity<?> findAllOrderDTOByCANCEL() {
        List<OrderDTO> orderDTOList = orderService.findAllOrderDTOByOrderStatusCANCEL(EOrderStatus.CANCEL.getValue());

        if (orderDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của order theo trạng thái CANCEL rỗng, không có dữ liệu hiển thị");
        }
        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }


    //Tìm thông tin của lẻ của orderId
    @GetMapping("/{id}")
    public ResponseEntity<?> displayOrderById(@PathVariable long id) {
        Optional<Order> orderOptional = orderService.findById(id);

        if (!orderOptional.isPresent()) {
            throw new ResourceNotFoundException("Mã Id order không tồn tại");
        }
        return new ResponseEntity<>(orderOptional.get().toOrderDTO(), HttpStatus.OK);
    }


    //4 Cập nhật trạng thái đơn hàng truyền vào orderId, xử lí tùy theo value trạng thái  truyền vào,
    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<?> doUpdateStatus(@PathVariable long orderId, @RequestBody OrderStatusDTO orderStatusDTO) {

        Optional<Order> orderOptional = orderService.findById(orderId);

        if (!orderOptional.isPresent()) {
            throw new ResourceNotFoundException("Mã Id order không tồn tại");
        }

        //Nơi ô select option hiển thị ra các trạng thái, để ta truyền value vào xử lí, để truyền value trạng thái vào
        Optional<OrderStatus> orderStatusOptional = orderStatusService.findById(orderStatusDTO.getId());
        if (!orderStatusOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy trạng thái này");
        }

        Order order = orderService.doChangeStatusOrder(orderOptional.get(), orderStatusOptional.get()); // truyền vào orderId, và orderId status

        return new ResponseEntity<>(order.toOrderDTO(), HttpStatus.OK);
    }

    //Tien
//    @GetMapping("/total-today")
//    public ResponseEntity<?> chartTotalToday() {
//        return new ResponseEntity<>(orderService.findTotalToday(), HttpStatus.OK);
//    }
    //

    //Tổng tiền theo trạng thái pending
    @GetMapping("/grand-total-pending")
    public ResponseEntity<?> grandTotalOrderPending() {
        GrandTotalPendingDTO grandTotalPendingDTO = orderService.findGrandTotalOrderStatusPending(EOrderStatus.PENDING.getValue());

        return new ResponseEntity<>(grandTotalPendingDTO, HttpStatus.OK);

    }

    //Đếm SL theo trạng thái pending
    @GetMapping("/count-pending")
    public ResponseEntity<?> countOrderIdPending() {
        CountDTO countDTO = orderService.findCountOrderStatusPending(EOrderStatus.PENDING.getValue());

        return new ResponseEntity<>(countDTO, HttpStatus.OK);

    }

    //Tổng tiền theo trạng thái completed
    @GetMapping("/grand-total-completed")
    public ResponseEntity<?> grandTotalOrderCompleted() {
        GrandTotalPendingDTO grandTotalPendingDTO = orderService.findGrandTotalOrderStatusCompleted(EOrderStatus.COMPLETED.getValue());

        return new ResponseEntity<>(grandTotalPendingDTO, HttpStatus.OK);

    }

    //Đếm SL theo trạng thái completed
    @GetMapping("/count-completed")
    public ResponseEntity<?> countOrderIdCompleted() {
        CountDTO countDTO = orderService.findCountOrderStatusCompleted(EOrderStatus.COMPLETED.getValue());

        return new ResponseEntity<>(countDTO, HttpStatus.OK);

    }

    //Đếm SL theo trạng thái cancel
    @GetMapping("/count-cancel")
    public ResponseEntity<?> countOrderIdCancel() {
        CountDTO countDTO = orderService.findCountOrderStatusCancel(EOrderStatus.CANCEL.getValue());

        return new ResponseEntity<>(countDTO, HttpStatus.OK);

    }


    //viết sp, tất cả dữ liệu order
    @GetMapping("/all-order-data")
    public ResponseEntity<List<?>> findAllOrderDTOData() {
        List<IOrderDTO> iOrderDTOList = orderService.findAllOrderDTOData();

        return new ResponseEntity<>(iOrderDTOList, HttpStatus.OK);

    }




    @GetMapping("/total-today")
    public ResponseEntity<?> orderTotalToday() {
        List<ITotalAnalytics> iTotalAnalyticsList = orderService.orderTotalToday();
        return new ResponseEntity<>(iTotalAnalyticsList, HttpStatus.OK);
    }

    @GetMapping("/total-week")
    public ResponseEntity<?> orderTotalWeek() {
        List<ITotalAnalytics> iTotalAnalyticsList = orderService.orderTotalWeek();
        return new ResponseEntity<>(iTotalAnalyticsList, HttpStatus.OK);
    }

    @GetMapping("/total-month")
    public ResponseEntity<?> orderTotalMonth() {
        List<ITotalAnalytics> iTotalAnalyticsList = orderService.orderTotalMonth();
        return new ResponseEntity<>(iTotalAnalyticsList, HttpStatus.OK);
    }
}

