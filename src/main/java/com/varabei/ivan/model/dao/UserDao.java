package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.User;

import java.util.List;

public interface UserDao{
    List<User> findAll() throws DaoException;
}
