package com.epam.pollWebApp.controller;

import com.epam.pollWebApp.dao.EmployeeDAO;
import com.epam.pollWebApp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@SessionAttributes("user")
public class Change {
    Employee employee = new Employee();
    private EmployeeDAO employeeDAO;

    @Autowired
    public Change(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @PostMapping("changePassword")
    public String changePass(@RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
                             @RequestParam(value = "email", required = false) String email,
                             Model model) {

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        String encodePassword = Base64.getEncoder().encodeToString(password.getBytes());
        String encodePasswordConfirm = Base64.getEncoder().encodeToString(passwordConfirm.getBytes());
        employee.setEmail(email);
        employee.setPassword(encodePassword);
        if (matcher.matches() && email.equals(employee.getEmail()) && encodePassword.contentEquals(encodePasswordConfirm)) {
            employeeDAO.updateUserPassword(employee);
            model.addAttribute("changePass", "Your password has been changed");
            return "index";
        } else {
            return "errorpage";
        }
    }

    @PostMapping("changeUsername")
    public String changeUsername(@RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "usernameConfirm", required = false) String usernameConfirm,
                                 Model model){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        employee.setEmail(email);
        employee.setUsername(username);
        if (matcher.matches() && email.equals(employee.getEmail()) && username.contentEquals(usernameConfirm)) {
            employeeDAO.updateUser(employee);
            model.addAttribute("changeUsername", "Your username has been changed");
            return "index";
        } else {
            return "errorpage";
        }
    }
}
