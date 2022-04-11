package com.epam.pollWebApp.dao;


import com.epam.pollWebApp.connector.JDBCConnector;
import com.epam.pollWebApp.model.Result;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("resultDAO")
public class ResultDAOImpl implements ResultDAO {
    private Connection connection = JDBCConnector.getInstance().getConnection();

    @Override
    public List<Result> findAll() {
        List<Result> resultList = new ArrayList<>();
        try {
            String query = "SELECT * FROM results";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Result result = new Result();
                result.setId(resultSet.getInt("id"));
                result.setExplanation(resultSet.getString("text"));
                result.setMaxScore(resultSet.getInt("min_score"));
                result.setMinScore(resultSet.getInt("max_score"));

                resultList.add(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getAll method");
        }
        return resultList;
    }

    @Override
    public Result findById(long id) {
        Result result = new Result();
        try {
            String query = "SELECT * FROM results WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.setId(resultSet.getInt("id"));
                result.setExplanation(resultSet.getString("explanation"));
                result.setMinScore(resultSet.getInt("min_score"));
                result.setMinScore(resultSet.getInt("max_score"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getById method");
        }

        return result;
    }

    @Override
    public List<Result> findByPollId(long pollId) {
        Result result = new Result();
        try {
            String query = "SELECT * FROM results WHERE poll_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, pollId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.setId(resultSet.getInt("id"));
                result.setExplanation(resultSet.getString("explanation"));
                result.setMinScore(resultSet.getInt("min_score"));
                result.setMinScore(resultSet.getInt("max_score"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getById method");
        }

        return null;
    }

    @Override
    public Result findByScore(long pollId, long score) {
        List<Result> resultList = new ArrayList<>();
        Result result = new Result();
        try {
            String query = "SELECT * FROM results WHERE poll_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, pollId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.setId(resultSet.getInt("id"));
                result.setExplanation(resultSet.getString("explanation"));
                result.setMinScore(resultSet.getLong("min_score"));
                result.setMaxScore(resultSet.getLong("max_score"));
                resultList.add(result);
                if (score >= result.getMinScore() && score <= result.getMaxScore()) {
                    return result;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
