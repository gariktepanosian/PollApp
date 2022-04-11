package com.epam.pollWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("user")
public class LogoutController {

    @PostMapping("/logout")
    public String logout (Model model, SessionStatus sessionStatus){
        sessionStatus.isComplete();
        model.addAttribute("logout", "You are successfully logged out!" );
        return "index";
    }
}
