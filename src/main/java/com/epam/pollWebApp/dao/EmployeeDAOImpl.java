package com.epam.pollWebApp.dao;

import com.epam.pollWebApp.connector.JDBCConnector;
import com.epam.pollWebApp.model.Employee;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("employeeDAO")
public class EmployeeDAOImpl implements EmployeeDAO<Employee> {
    private Connection connection = JDBCConnector.getInstance().getConnection();

    public List<Employee> getAll() {
        List<Employee> users = new ArrayList<>();
        try {
            String query = "SELECT * From employee";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee user = new Employee();
                user.setId(resultSet.getInt("id"));
                user.setFirst_name(resultSet.getString("first_name"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.setContact(resultSet.getString("contact"));
                user.setDate(resultSet.getDate("result_date"));
                user.setPoll_result(resultSet.getString("poll_result"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void createUser(Employee obj) {
        try {
            String query = "INSERT INTO employee(first_name,email,username,password,address,contact) VALUES(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, obj.getFirst_name());
            statement.setString(2, obj.getEmail());
            statement.setString(3, obj.getUsername());
            statement.setString(4, obj.getPassword());
            statement.setString(5, obj.getAddress());
            statement.setString(6, obj.getContact());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(Employee obj) {
        try {
            String query = "UPDATE employee SET username=? WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, obj.getUsername());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserPassword(Employee obj) {
        try {
            String query = "UPDATE employee SET password=? WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, obj.getPassword());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existUsernameAndPass(String username, String password) {
        boolean exist = false;
        try {
            String query = "SELECT firstname, email, username, address FROM employee WHERE username=? and password= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public Employee getUsernameAndPass(String username, String password) {
        Employee user = new Employee();
        try {
            String query = "SELECT * FROM employee WHERE username=? and password= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirst_name(resultSet.getString("first_name"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAddress(resultSet.getString("address"));
                user.setContact(resultSet.getString("contact"));
                user.setDate(resultSet.getDate("result_date"));
                user.setPoll_result(resultSet.getString("poll_result"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateResult(int id, String description) {
        try {
            String query = "UPDATE employee SET poll_result = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in update result method");
        }
    }

    @Override
    public boolean validate(Employee employee) {
        boolean status = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from employee where username=? and password=?");
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2, employee.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            status = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean validateRegister(Employee employee) {
        boolean status = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from employee where username=?");
            preparedStatement.setString(1, employee.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();
            status = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean validateEmail(Employee employee) {
        boolean status = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from employee where email=?");
            preparedStatement.setString(1, employee.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            status = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public void updateDate(int id, Date date) {
        try {
            String query = "UPDATE employee SET result_date = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in update date method");
        }
    }

    @Override
    public Employee pollResultById(long id) {
        Employee user = new Employee();
        try {
            String query = "SELECT poll_result FROM employee WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setPoll_result(resultSet.getString("poll_result"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getById method");
        }
        return user;
    }
}
