package com.epam.pollWebApp.dao;

import com.epam.pollWebApp.model.Result;

import java.util.List;

public interface ResultDAO extends PollDAO<Result> {

    List<Result> findByPollId(long pollId);

    Result findByScore(long pollId, long score);
}
