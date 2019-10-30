package com.netcracker.dragun.service;

import com.netcracker.dragun.entity.User;
import com.netcracker.dragun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {

    private final UserRepository UserRepository;

    @Autowired
    public UserService (UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }
    public User get(Long id) {
        return UserRepository.findById(id).get();
    }
    public List<User> getAll() {
        return UserRepository.findAll();
    }
}
