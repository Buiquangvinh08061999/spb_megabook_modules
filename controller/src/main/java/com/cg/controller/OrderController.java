package com.cg.controller;


import com.cg.domain.dto.ItemDTO;
import com.cg.domain.dto.OrderDTO;
import com.cg.domain.dto.OrderItemDTO;
import com.cg.domain.dto.ProductDTO;
import com.cg.domain.entity.*;
import com.cg.service.order.IOrderService;
import com.cg.service.orderItem.IOrderItemService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.util.*;


@Controller
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IUserService userService;

    private String getPrincipal(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        }else {
            username ="";
        }
        return username;
    }
    private User getUser(){
        String username = getPrincipal();

        Optional<User> userOptional = userService.findByUsername(username);
        if(!userOptional.isPresent()){
            return null;
        }
        return userOptional.get();
    }


    @GetMapping
    public ModelAndView showListOrder() {
        ModelAndView modelAndView = new ModelAndView("order/list");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }



    @GetMapping("/create")
    public ModelAndView showCreateOrder() {
        ModelAndView modelAndView = new ModelAndView("order/create");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }

    @GetMapping("/detail")
    public ModelAndView showOrderDetail() {
        ModelAndView modelAndView = new ModelAndView("order/detail");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }


    @GetMapping("/pending")
    public ModelAndView showOrderPending() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/pending");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }



    @GetMapping("/completed")
    public ModelAndView showOrderCompleted() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/completed");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }



    @GetMapping("/canceled")
    public ModelAndView showOrderCancel() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/canceled");
        modelAndView.addObject("user", getUser());

        return modelAndView;
    }

    //Vinh làm các sản phẩm đơn hàng, tìm  orderId truyền vào trong OrderItem, có bao nhiêu sản phẩm thì sẽ sổ ra bấy nhiêu
    @GetMapping("/detail/{orderId}")
    public ModelAndView showOrderDetailById(@PathVariable Long orderId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/detail");
        modelAndView.addObject("user", getUser());

        List<OrderItemDTO> orderItemDTOList = orderItemService.findAllByOrderId(orderId);
        int sum =0;
        for (OrderItemDTO orderItemDTO: orderItemDTOList){
            System.out.println(orderItemDTO);
            sum+= orderItemDTO.getItemQuantity();
        }

        modelAndView.addObject("sumAllQuantity", sum); //SL 2 hoặc nhiều của orderItem quantity thuộc vào 1 order Id cộng lại để hiển thị chi tiết tổng SL,

        modelAndView.addObject("orderItemList", orderItemDTOList);


        Optional<Order> orderOptional = orderService.findById(orderId); //Lấy tất cả order theo id truyền vào
        Customer customer = orderOptional.get().getCustomer(); //lấy tất cả các trường customer theo Order id truyền vào .get customer()

        modelAndView.addObject("order", orderOptional.get().toOrderDTO());
        modelAndView.addObject("customer", customer);


        return modelAndView;
    }
}
