package com.epam.pollWebApp.controller;

import com.epam.pollWebApp.dao.AnswerDAO;
import com.epam.pollWebApp.dao.EmployeeDAO;
import com.epam.pollWebApp.dao.ResultDAO;
import com.epam.pollWebApp.model.Employee;
import com.epam.pollWebApp.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user")
public class ResultsController {

    AnswerDAO answerDAO;
    ResultDAO resultDAO;
    EmployeeDAO employeeDAO;

    long sum = 0;

    @Autowired
    public ResultsController(AnswerDAO answerDAO, ResultDAO resultDAO, EmployeeDAO employeeDAO) {
        this.answerDAO = answerDAO;
        this.resultDAO = resultDAO;
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("/results")
    public String results(@ModelAttribute("user") Employee user, HttpServletRequest req, Model model) {

        String[] questionIds = req.getParameterValues("questionId");

        for (String questionId : questionIds) {
            String parameter = req.getParameter("marked" + questionId);
            if (parameter == null) {
                model.addAttribute("error", "You have not filled in all the fields, try again");
                return "errorpage";
            } else {
                long value = Long.parseLong(parameter);
                sum += value;
            }
        }

        String username = user.getUsername();
        String password = user.getPassword();
        int id = user.getId();
        Result expByScore = resultDAO.findByScore(1, sum);

        Date now = Date.valueOf(LocalDate.now());

        employeeDAO.getUsernameAndPass(username, password);
        employeeDAO.updateDate(id, now);

        String explanation = expByScore.getExplanation();
        employeeDAO.updateResult(id, explanation);
        Employee userResult = employeeDAO.pollResultById(id);

        model.addAttribute("userResult", userResult);
        return "results";
    }
}
