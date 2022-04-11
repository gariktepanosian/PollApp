package com.epam.pollWebApp.dao;

import com.epam.pollWebApp.connector.JDBCConnector;
import com.epam.pollWebApp.model.Poll;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("pollDAO")
public class PollDAOImpl implements PollDAO {
    private Connection connection = JDBCConnector.getInstance().getConnection();

    @Override
    public List<Poll> findAll() {
        List<Poll> poll = new ArrayList<>();
        try {
            String query = "SELECT * FROM poll";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Poll poll1 = new Poll();
                poll1.setId(resultSet.getLong("id"));
                poll1.setName(resultSet.getString("name"));
                poll1.setDescription(resultSet.getString("description"));
                poll.add(poll1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getAll method");
        }
        return poll;
    }

    @Override
    public Poll findById(long id) {
        Poll poll = new Poll();
        try {
            String query = "SELECT * FROM poll WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poll.setId(resultSet.getLong("id"));
                poll.setName(resultSet.getString("name"));
                poll.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getById method");
        }
        return poll;
    }
}
