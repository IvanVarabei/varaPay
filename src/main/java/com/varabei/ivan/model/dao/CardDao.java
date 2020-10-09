package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Card;

import java.util.Optional;

public interface CardDao {
    Optional<Card> findById(Long id) throws DaoException;
}
