package com.epam.pollWebApp.dao;



import com.epam.pollWebApp.model.Answer;

import java.util.List;

public interface AnswerDAO extends PollDAO<Answer> {

    List<Answer> findByQuestionId(long questionId);
}
