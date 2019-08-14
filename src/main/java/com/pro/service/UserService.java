package com.pro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.dao.UserDao;
import com.pro.entity.User;

@Service
public class UserService{

    @Autowired
    private UserDao userRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void edit(User user) {
        userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}


