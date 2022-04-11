package com.epam.pollWebApp.controller;

import com.epam.pollWebApp.dao.EmployeeDAO;
import com.epam.pollWebApp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Base64;
import java.util.List;

@Controller
@SessionAttributes("user")
public class UsersLoginController {

    private EmployeeDAO employeeDAO;

    @Autowired
    public UsersLoginController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping
    public String openLoginPage() {
        return "index";
    }

    @PostMapping("/login")
    public String validDate(@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "password", required = false) String password,
                            Model model) {
        String encodePassword = Base64.getEncoder().encodeToString(password.getBytes());
         Employee employee = new Employee();
        employee.setUsername(username);
        employee.setPassword(encodePassword);
        if (employeeDAO.validate(employee)) {
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            List<Employee> allUser = employeeDAO.getAll();
            for (Employee employee1 : allUser) {
                if (username.equals(employee1.getUsername()) && encodePassword.equals(employee1.getPassword())) {
                    model.addAttribute("user", employee1);
                    break;
                }
            }
            return "loginsuccess";
        } else  {
            model.addAttribute("errorMassage", "Something wrong");
            return "index";
        }
    }
}

