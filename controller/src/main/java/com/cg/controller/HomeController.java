package com.cg.controller;

import com.cg.domain.dto.UserDTO;
import com.cg.domain.entity.User;
import com.cg.service.locationRegion.ILocationRegionService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    ILocationRegionService ILocationRegionService;

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
    public ModelAndView showHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");

        modelAndView.addObject("user", getUser());

        return modelAndView;
    }
}

