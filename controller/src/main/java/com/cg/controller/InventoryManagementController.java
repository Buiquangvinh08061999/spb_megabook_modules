package com.cg.controller;

import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.entity.*;
import com.cg.service.importOrder.IImportOrderService;
import com.cg.service.importOrderItem.IImportOrderItemService;
import com.cg.service.supplier.ISupplierService;
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
@RequestMapping("/inventory-management")
public class InventoryManagementController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IImportOrderService importOrderService;

    @Autowired
    private IImportOrderItemService importOrderItemService;

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
    public ModelAndView showInventory(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/list");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/more-than-0")
    public ModelAndView showInventoryMoreThan0(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/inventory-more-than-0");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/is-0")
    public ModelAndView showInventoryIs0(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/inventory-is-zero");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }


    @GetMapping("/suppliers")
    public ModelAndView showSuppliers(){
        ModelAndView modelAndView = new ModelAndView("inventory-management/suppliers");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/suppliers-detail/{supplierId}")
    public ModelAndView showSuppliersDetail(@PathVariable Long supplierId){
        ModelAndView modelAndView = new ModelAndView("inventory-management/suppliers-detail");
        modelAndView.addObject("user", getUser());

        Optional<Supplier> supplierOptional = supplierService.findById(supplierId);
        modelAndView.addObject("supplier", supplierOptional.get());

        LocationRegion locationRegion = supplierOptional.get().getLocationRegion();
        modelAndView.addObject("locationRegion", locationRegion);

        return modelAndView;
    }
    @GetMapping("/warehouses")
    public ModelAndView showDepots(){
        ModelAndView modelAndView = new ModelAndView("inventory-management/warehouses");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/create-warehouse")
    public ModelAndView showCreateDepot(){
        ModelAndView modelAndView = new ModelAndView("inventory-management/create-warehouse");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }

    @GetMapping("/create-suppliers")
    public ModelAndView showCreateSuppliers(){
        ModelAndView modelAndView = new ModelAndView("inventory-management/create-suppliers");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }


    @GetMapping("/import-list")
    public ModelAndView showOrderList(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/import-list");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/import-pending")
    public ModelAndView showOrderToSupplierPendingList(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/import-pending");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/import-completed")
    public ModelAndView showOrderToSupplierCompletedList(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/import-completed");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }
    @GetMapping("/import-canceled")
    public ModelAndView showOrderToSupplierCancelList(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/import-canceled");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }

    @GetMapping("/create-import")
    public ModelAndView showCreateOrder(){
        ModelAndView modelAndView=new ModelAndView("inventory-management/create-import");
        modelAndView.addObject("user", getUser());
        return modelAndView;
    }

    @GetMapping("/import-order-detail/{importId}")
    public ModelAndView showImportOrderDetail(@PathVariable Long importId){
        ModelAndView modelAndView=new ModelAndView("inventory-management/import-order-detail");

        modelAndView.addObject("user", getUser());

        Optional<ImportOrder> importOrderOptional = importOrderService.findById(importId);
        modelAndView.addObject("importOrder", importOrderOptional.get());

        User userr = importOrderOptional.get().getUser();
        modelAndView.addObject("userr", userr);

        Supplier supplier = importOrderOptional.get().getSupplier();
        modelAndView.addObject("supplier", supplier);

        LocationRegion locationSupplier = supplier.getLocationRegion();
        modelAndView.addObject("locationSupplier", locationSupplier);

        Warehouse warehouse = importOrderOptional.get().getWarehouse();
        modelAndView.addObject("warehouse", warehouse);

        LocationRegion locationWarehouse = warehouse.getLocationRegion();
        modelAndView.addObject("locationWarehouse", locationWarehouse);

        List<ImportOrderItemDTO> importOrderItemList = importOrderItemService.findAllByOrderId(importId);
        modelAndView.addObject("importOrderItemList", importOrderItemList);

        return modelAndView;
    }
}
