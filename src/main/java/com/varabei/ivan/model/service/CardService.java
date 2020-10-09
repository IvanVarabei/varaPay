package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Card;

import java.util.Optional;

public interface CardService {
    Optional<Card> findById(Long id) throws ServiceException;
}
