package com.cg.controller;


import com.cg.domain.dto.*;
import com.cg.domain.entity.*;
import com.cg.service.category.ICategoryService;
import com.cg.service.item.IItemService;
import com.cg.service.product.IProductService;

import com.cg.service.publisher.IPublisherService;
import com.cg.service.unit.IUnitService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    IItemService itemService;

    @Autowired
    IUnitService unitService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IPublisherService publisherService;

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
    public ModelAndView showProductList(){
        ModelAndView modelAndView=new ModelAndView("product/list");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView showCreateProductModel(){
        ModelAndView modelAndView=new ModelAndView("product/create");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }



    @GetMapping("/detail/{id}")
    public ModelAndView showProductDetail(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product/detail");
        modelAndView.addObject("user", getUser());

        Optional<Product> productOptional = productService.findById(id);
        modelAndView.addObject("product", productOptional.get());

        Product product = productOptional.get();

        Category category = product.getCategory();
        Unit unit = product.getUnit();
        Publisher publisher = product.getPublisher();

        modelAndView.addObject("category", category);
        modelAndView.addObject("unit", unit);
        modelAndView.addObject("publisher", publisher);

        return modelAndView;
    }

    @GetMapping("/detail-inventory/{id}")
    public ModelAndView showProductDetailInventory(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product/detail-inventory");
        modelAndView.addObject("user", getUser());

        Optional<Item> itemOptional = itemService.findById(id);
        Product product = itemOptional.get().getProduct();
        Unit unit = product.getUnit();
        modelAndView.addObject("item", itemOptional.get());
        modelAndView.addObject("product", product);
        modelAndView.addObject("unit", unit);

        return modelAndView;
    }
}
