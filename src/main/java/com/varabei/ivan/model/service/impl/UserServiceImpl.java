package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.util.CustomSecurity;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserDao userDao =  DaoFactory.getInstance().getUserDao();
    private static final int SALT_LENGTH = 64;

    @Override
    public void signUp(User user) throws ServiceException {
        try {
            StringBuilder password = new StringBuilder(user.getPassword());
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            String hashedPassword = CustomSecurity.generateHash(password.append(salt).toString());
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        try {
            Optional<User> user = userDao.findByLogin(login);
            if(user.isPresent()) {
                String hashedPassword = user.get().getPassword();
                String salt = user.get().getSalt();
                if(hashedPassword.equals(CustomSecurity.generateHash(password + salt))){
                    return user;
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws ServiceException {
        try {
            StringBuilder password = new StringBuilder(newPassword);
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            String hashedPassword = CustomSecurity.generateHash(password.append(salt).toString());
            userDao.updatePassword(email, hashedPassword, salt);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void updatePassword(Long id, String newPassword) throws ServiceException {
        try {
            StringBuilder password = new StringBuilder(newPassword);
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            String hashedPassword = CustomSecurity.generateHash(password.append(salt).toString());
            userDao.updatePassword(id, hashedPassword, salt);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public boolean checkPresenceByIdPassword(Long id, String password) throws ServiceException {
        try {
            Optional<User> user = userDao.findById(id);
            if(user.isPresent()) {
                String hashedPassword = user.get().getPassword();
                String salt = user.get().getSalt();
                return hashedPassword.equals(CustomSecurity.generateHash(password + salt));
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
    ////////////////////////////////////////

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Optional<User> findById(Long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}


//public class UserServiceImpl implements UserService {
//    private static final UserDao userDao =  DaoFactory.getInstance().getUserDao();
//
//    @Override
//    public Optional<User> signIn(String login, String password) throws ServiceException {
//        try {
//            return userDao.findByLoginPassword(login, password);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//    }
//
//    @Override
//    public void signUp(User user) throws ServiceException {
//        try {
//            userDao.create(user);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public List<User> findAll() throws ServiceException {
//        try {
//            return userDao.findAll();
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public Optional<User> findByLogin(String login) throws ServiceException {
//        try {
//            return userDao.findByLogin(login);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public Optional<User> findById(Long id) throws ServiceException {
//        try {
//            return userDao.findById(id);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public Optional<User> findByEmail(String email) throws ServiceException {
//        try {
//            return userDao.findByEmail(email);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public void updatePassword(String email, String newPassword) throws ServiceException {
//        try {
//             userDao.updatePassword(email, newPassword);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public void updatePassword(Long id, String newPassword) throws ServiceException {
//        try {
//            userDao.updatePassword(id, newPassword);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//
//    @Override
//    public boolean checkPresenceByIdPassword(Long id, String password) throws ServiceException {
//        try {
//            return userDao.checkPresenceByIdPassword(id, password);
//        } catch (DaoException e) {
//            throw new ServiceException("", e);
//        }
//    }
//}
