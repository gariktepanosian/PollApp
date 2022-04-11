package com.epam.pollWebApp.controller;


import com.epam.pollWebApp.dao.EmployeeDAO;
import com.epam.pollWebApp.dao.PollDAO;
import com.epam.pollWebApp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("user")
public class PollsController {

   private PollDAO pollDAO;
   private EmployeeDAO employeeDAO;

    int today = 0;
    int day = 7;
    Date date = new Date(0000, 00, 00);

    @Autowired
    public PollsController(PollDAO pollDAO, EmployeeDAO employeeDAO) {
        this.pollDAO = pollDAO;
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("/poll")
    public String openPollPage(@ModelAttribute("user") Employee employee, Model model) {

        int id = employee.getId();
        Date result_date = employee.getDate();


        if (result_date == null || result_date.toLocalDate().getDayOfMonth() + 7 < today) {
            employeeDAO.updateDate(id, this.date);
            List all = pollDAO.findAll();
            model.addAttribute("polls", all);
            return "poll";
        }else  {
            int resultDate = result_date.toLocalDate().getDayOfMonth();
            today = LocalDate.now().getDayOfMonth();
            day += resultDate;
            if (day >= today) {
                Employee byId = employeeDAO.pollResultById(id);
                model.addAttribute("poll_error", "You can take the survey again in a week\n Your score is -");
                model.addAttribute("poll_result", byId);
                return "results";
            }
        }
        return null;
    }
}

