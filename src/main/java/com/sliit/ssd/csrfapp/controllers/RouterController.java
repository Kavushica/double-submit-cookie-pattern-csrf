package com.sliit.ssd.csrfapp.controllers;

import com.sliit.ssd.csrfapp.models.Credentials;
import com.sliit.ssd.csrfapp.models.Fund;
import com.sliit.ssd.csrfapp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rkavushica on 9/9/18.
 */

@Controller
public class RouterController {

    @Autowired
    LoginService authService;

    @GetMapping("/")
    public String showLoginPage(Model model, HttpServletRequest request){

        if (!authService.isAuthenticated(request.getCookies())){
            model.addAttribute("login", new Credentials());
            return "redirect:/login";
        } else {
            model.addAttribute("home", new Fund());
            return "redirect:/home";
        }

    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("login", new Credentials());
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpServletRequest request){
        return redirect(model, request);
    }

    @GetMapping("/transfer")
    public String showTransferPage(Model model, HttpServletRequest request){
        return redirect(model, request);
    }

    private String redirect(Model model, HttpServletRequest request) {
        if(authService.isAuthenticated(request.getCookies())){
            model.addAttribute("home", new Fund());
            return "home";
        } else {
            model.addAttribute("login", new Credentials());
            return "redirect:/login";
        }
    }
}
