package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void signUp(User user) throws ServiceException;

    List<User> findAll() throws ServiceException;

    Optional<User> signIn(String login, String password) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findById(Long id) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    void updatePassword(String email, String newPassword) throws ServiceException;

    void updatePassword(Long id, String newPassword) throws ServiceException;

    boolean checkPresenceByIdPassword(Long id, String password) throws ServiceException;
}
