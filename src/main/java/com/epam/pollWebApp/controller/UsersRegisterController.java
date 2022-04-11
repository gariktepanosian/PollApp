package com.epam.pollWebApp.controller;

import com.epam.pollWebApp.dao.EmployeeDAO;
import com.epam.pollWebApp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UsersRegisterController {
    Employee employee = new Employee();
    private EmployeeDAO employeeDAO;

    @Autowired
    public UsersRegisterController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("/register")
    public String openRegisterPage(Model model) {
        model.addAttribute("user");
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam(value = "first_name", required = false) String first_name,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "address", required = false) String address,
                           @RequestParam(value = "contact", required = false) String contact, Model model) {

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        String encodePassword = Base64.getEncoder().encodeToString(password.getBytes());
        employee.setFirst_name(first_name);
        employee.setEmail(email);
        employee.setUsername(username);
        if (employeeDAO.validateRegister(employee)) {
            model.addAttribute("username", username);
            if (username.equals(employee.getUsername()) || email.equals(employee.getEmail())) {
                return "index";
            }
        } else if (matcher.matches()) {
            employee.setPassword(encodePassword);
            employee.setAddress(address);
            employee.setContact(contact);
            employeeDAO.createUser(employee);
            model.addAttribute("registerMessage", "You have successfully registered!");
            return "index";
        } else {
            model.addAttribute("registerMessageException", "Have the mistakes");
            return "register";
        }
    return "index";
    }
    }


