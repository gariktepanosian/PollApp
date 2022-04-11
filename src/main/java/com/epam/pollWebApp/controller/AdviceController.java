package com.epam.pollWebApp.controller;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex, Model model) {
        model.addAttribute("errorPage", "404");
        return "/error";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleBean(Exception ex, Model model) {
        model.addAttribute("errorPage", "500");
        return "/error";
    }
}