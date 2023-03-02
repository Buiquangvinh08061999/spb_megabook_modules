package com.cg.service.order;

import com.cg.domain.dto.*;
import com.cg.domain.entity.*;
import com.cg.exception.DataInputException;
import com.cg.repository.ItemRepository;
import com.cg.repository.OrderItemRepository;
import com.cg.repository.OrderRepository;
import com.cg.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional // dùng để roolblack lại dữ liệu, nếu có lỗi tất cả dữ liệu sẽ reset lại hết
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    //Lưu lại đối tượng Order
    @Override
    public Order save(Order order) {

        return orderRepository.save(order);
    }

    @Override
    public void remove(Long id) {

    }

    //1. Hiển thị tất cả phần order dựa theo customer_id và trạng thái, Nếu khác trạng thái sẽ tạo đơn hàng mới(api/order/add)
    @Override
    public Optional<Order> findOrderByCustomerIdAndTitleEn(Long id, String titleEn) {
        return orderRepository.findOrderByCustomerIdAndTitleEn(id, titleEn);
    }

    //2.Hiển thị list order theo trạng thái pending(đang xử lí)
    @Override
    public List<OrderDTO> findAllOrderDTOByOrderStatusPENDING(String pending) {
        return orderRepository.findAllOrderDTOByOrderStatusPENDING(pending);
    }

    //.Hiển thị order theo trạng thái COMPLETED(đã hoàn thành)
    @Override
    public List<OrderDTO> findAllOrderDTOByOrderStatusCOMPLETED(String completed) {
        return orderRepository.findAllOrderDTOByOrderStatusCOMPLETED(completed);
    }

    // Hiển thị order theo trạng thái CANCEL(đã hủy đơn hàng vận chuyển)
    @Override
    public List<OrderDTO> findAllOrderDTOByOrderStatusCANCEL(String cancel) {
        return orderRepository.findAllOrderDTOByOrderStatusCANCEL(cancel);
    }


    //4 Cập nhật trạng thái đơn hàng, truyền vào orderId và orderStatus Id,
    @Override
    public Order doChangeStatusOrder(Order order, OrderStatus orderStatus) {

        order.setOrderStatus(orderStatus);      // lưu trạng thái mới vào order, trạng thái cũ sẽ bị hủy bỏ

        Order newOrder = orderRepository.save(order); //lưu lại orderId đó,


        //COMPLETED: đã hoàn thành , thì ta phải - SL trong kho, và + SL đã bán
        if(orderStatus.getId() == 2){

            List<OrderItemDTO> orderItemDTOList = orderItemRepository.findAllByOrderId(order.getId());

            for (OrderItemDTO orderItemDTO: orderItemDTOList){//Lấy ra dữ liệu OrderItem hiện tại

                ItemDTO itemDTO = orderItemDTO.getItem();      //Lấy ra dữ liệu Item hiện tại ra để so sánh sử dụng thuật toán

                if(orderItemDTO.getItemQuantity() > itemDTO.getAvailable()){
                    throw new DataInputException("Sản phẩm này đã bán hết vui lòng cập nhật thêm số lượng, để đáp ứng đơn hàng, không thể thay đổi trạng thái");
                }

                itemDTO.setAvailable(itemDTO.getAvailable() - orderItemDTO.getItemQuantity());
                itemDTO.setSold(itemDTO.getSold() + orderItemDTO.getItemQuantity());

                itemRepository.save(itemDTO.toItem());

            }
        }


        //Sang trại thái 3 là đã hủy, thì chỉ cần chuyển qua đã hủy thôi


        return newOrder;
    }


    //6.Tìm list order dựa vào customerId truyền vào, tính tổng tiền của customerId đó, với điệu kiện trạng thái phải hoàn thành 3 4 thì mới tính tổng
    @Override
    public List<OrderDTO> findByCustomerId(Long customerId,String pending, String cancel) {
        return orderRepository.findByCustomerId(customerId, pending, cancel);
    }



    //add order
    @Override
    public void createOrder(List<OrderItemDTO> orderItemDTOList, Customer customer) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(1L);

        Order order = new Order();
        order.setId(0L);
        order.setOrderStatus(orderStatusOptional.get());
        order.setCustomer(customer);

        BigDecimal grandTotal = new BigDecimal(0L);
        order.setGrandTotal(grandTotal);

        Order newOrder = orderRepository.save(order);

        for (OrderItemDTO item : orderItemDTOList){            //có dữ liệu tính toán từ List<OrderItemDTO> orderItemDTOList truyền vào, ta lấy được giá, và số lượng được đặt(2) gán vào 1 đối tượng orderItem mới
            String title = item.getProductTitle();
            String sku = item.getProductSku();
            BigDecimal price  = item.getItemPrice();

            int quantity = item.getItemQuantity();

            grandTotal = grandTotal.add(item.getTotalPrice());    //0 + lấy itemTotal từ list orderItemDTOList (100k)

            OrderItem orderItem = new OrderItem();
            orderItem.setProductTitle(title);
            orderItem.setProductSku(sku);
            orderItem.setItemQuantity(quantity);
            orderItem.setItemPrice(price);
            orderItem.setTotalPrice(item.getTotalPrice());  //set tiền đã tính được vào ở orderItemDTOList
            orderItem.setOrder(newOrder);
            orderItem.setItem(item.getItem().toItem());   //Id Item của từng thằng

            orderItemRepository.save(orderItem);
        }

        newOrder.setGrandTotal(grandTotal); //set giá tiền lại ở orderId

        orderRepository.save(newOrder);

    }


    @Override
    public GrandTotalPendingDTO findGrandTotalOrderStatusPending(String pending) {
        return orderRepository.findGrandTotalOrderStatusPending(pending);
    }

    @Override
    public CountDTO findCountOrderStatusPending(String pending) {
        return orderRepository.findCountOrderStatusPending(pending);
    }

    @Override
    public GrandTotalPendingDTO findGrandTotalOrderStatusCompleted(String completed) {
        return orderRepository.findGrandTotalOrderStatusCompleted(completed);
    }

    @Override
    public CountDTO findCountOrderStatusCompleted(String completed) {
        return orderRepository.findCountOrderStatusCompleted(completed);
    }

    @Override
    public CountDTO findCountOrderStatusCancel(String cancel) {
        return orderRepository.findCountOrderStatusCancel(cancel);
    }

    @Override
    public List<OrderDTO> findAllOrdersDTO() {
        return orderRepository.findAllOrdersDTO();
    }



    //Vinh viết stored procedure
    @Override
    public List<IOrderDTO> findAllOrderDTOData() {
        return orderRepository.findAllOrderDTOData();
    }





    @Override
    public List<ITotalAnalytics> orderTotalToday() {
        return orderRepository.orderTotalToday();
    }

    @Override
    public List<ITotalAnalytics> orderTotalWeek() {
        return orderRepository.orderTotalWeek();
    }

    @Override
    public List<ITotalAnalytics> orderTotalMonth() {
        return orderRepository.orderTotalMonth();
    }
}
