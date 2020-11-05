package com.epam.varapay.model.service;

import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    String createCardAndReturnCvc(Long accountId) throws ServiceException;

    Optional<Card> findById(Long id) throws ServiceException;

    List<Card> findByAccountId(Long accountId) throws ServiceException;

    void delete(Long cardId) throws ServiceException;
}
