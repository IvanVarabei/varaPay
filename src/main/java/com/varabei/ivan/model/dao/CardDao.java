package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CardDao {
    void create(Long accountId) throws DaoException;

    Optional<Card> findById(Long id) throws DaoException;

    List<Card> findByAccountId(Long accountId) throws DaoException;

    void delete(Long cardId) throws DaoException;
}
