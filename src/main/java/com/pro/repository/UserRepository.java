package com.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    void deleteById(Long id);
}