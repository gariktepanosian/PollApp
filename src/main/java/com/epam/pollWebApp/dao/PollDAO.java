package com.epam.pollWebApp.dao;

import java.util.List;

public interface PollDAO<T> {

    List<T> findAll();

    T findById(long id);
}
