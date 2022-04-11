package com.epam.pollWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForgotController {

    @GetMapping("forgot")
    public String getIndex() {
        return "forgot";
    }

    @GetMapping("forgotPassword")
    public String getPass() {
        return "forgotPassword";
    }

    @GetMapping("forgotUsername")
    public String getUsername() {
        return "forgotUsername";
    }
}
