package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CardService {
    String createCardAndReturnCvc(Long accountId) throws ServiceException;

    Optional<Card> findById(Long id) throws ServiceException;

    List<Card> findByAccountId(Long accountId) throws ServiceException;

    void delete(Long cardId) throws ServiceException;
}
