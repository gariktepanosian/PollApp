package com.epam.pollWebApp.dao;

import com.epam.pollWebApp.model.Employee;

import java.sql.Date;
import java.util.List;

public interface EmployeeDAO<T> {

    List<T> getAll();

    void createUser(T obj);

    void updateUser(T obj);

    boolean existUsernameAndPass(String username, String password);

    T getUsernameAndPass(String username, String password);

    void updateUserPassword(T obj);

    boolean validate(Employee employee);

    boolean validateRegister(Employee employee);

    void updateDate(int id, Date date);

    Employee pollResultById(long id);

    void updateResult(int id, String description);
}
