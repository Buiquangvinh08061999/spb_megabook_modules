package com.cg.controller;

import com.cg.domain.dto.OrderDTO;
import com.cg.domain.entity.Customer;
import com.cg.domain.entity.LocationRegion;
import com.cg.domain.entity.User;
import com.cg.domain.enums.EOrderStatus;
import com.cg.service.customer.ICustomerService;
import com.cg.service.order.IOrderService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.util.Optional;


@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @Autowired
    private IOrderService orderService;

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
    public ModelAndView showPageCustomerList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/list");

        modelAndView.addObject("user", getUser());

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCustomerCreatePage() {
        ModelAndView modelAndView = new ModelAndView("customer/create");

        modelAndView.addObject("user", getUser());

        return modelAndView;
    }

    @GetMapping("/detail/{customerId}")
    public ModelAndView showCustomerDetail(@PathVariable Long customerId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/detail");

        Optional<Customer> customerOptional = customerService.findById(customerId);
        LocationRegion locationRegion = customerOptional.get().getLocationRegion();

        modelAndView.addObject("customer", customerOptional.get());
        modelAndView.addObject("locationRegion", locationRegion);

        modelAndView.addObject("user", getUser());

        //Tìm tất cả order theo customer id truyền vào
        List<OrderDTO> orderDTOList = orderService.findByCustomerId(customerId, EOrderStatus.PENDING.getValue(),EOrderStatus.CANCEL.getValue());
        String messError;
        if(orderDTOList.isEmpty()){
            messError = "Người dùng chưa mua đơn hàng nào cả!";
            return modelAndView.addObject("messError", messError);
        }

        BigDecimal sum = new BigDecimal(0);        //cách triển khai với kiểu bigdecimal
        BigDecimal sumDivide = new BigDecimal(0);
        for (OrderDTO orderDTO : orderDTOList){

            sum = sum.add(orderDTO.getGrandTotal());
        }
        sumDivide = sum.divide(new BigDecimal(orderDTOList.size())); //tạo biến sumGiaTriTB = sum(tổng tiền) / size sẽ ra được giá trị Tổng tiền giá trị TB

        System.out.println(sum);
        System.out.println(sumDivide);

        modelAndView.addObject("sum", sum);                           //(Giá trị tổng đơn hàng tất cả khi đã thanh toán trạng thái 3 4)
        modelAndView.addObject("sumDivide", sumDivide);               //Giá trị trung bình đơn hàng= (tổng sum / size sẽ ra giá trị trung bình)
        modelAndView.addObject("size", orderDTOList.size());          //Tổng đơn hàng đếm theo list là lấy được

        modelAndView.addObject("orderDTOList", orderDTOList);         //Phần cuối sổ ra tất cả danh sách đơn hàng, ở cuối footer

        return modelAndView;
    }





}
