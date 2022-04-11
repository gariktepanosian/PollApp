package com.epam.pollWebApp.dao;

import com.epam.pollWebApp.connector.JDBCConnector;
import com.epam.pollWebApp.model.Answer;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("answerDAO")
public class AnswerDAOImpl implements AnswerDAO {
    private Connection connection = JDBCConnector.getInstance().getConnection();

    @Override
    public List<Answer> findAll() {
        List<Answer> question = new ArrayList<>();
        try {
            String query = "SELECT * FROM answer";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setText(resultSet.getString("ans_text"));
                question.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getAll method");
        }
        return question;
    }

    @Override
    public Answer findById(long id) {
        Answer answer = new Answer();
        try {
            String query = "SELECT * FROM answer WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                answer.setWeight(resultSet.getInt("weight"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getById method");
        }

        return answer;
    }

    @Override
    public List<Answer> findByQuestionId(long questionId) {
        List<Answer> answer = new ArrayList<>();
        try {
            String query = "SELECT * FROM question INNER JOIN answer ON question.quest_id=answer.question_id WHERE question_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, questionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Answer answer1 = new Answer();
                answer1.setId(resultSet.getLong("id"));
                answer1.setText(resultSet.getString("ans_text"));
                answer1.setWeight(resultSet.getLong("weight"));
                answer1.setQuestion_id(resultSet.getLong("question_id"));
                answer.add(answer1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Something is went wrong in getById method");
        }
        return answer;
    }
}
