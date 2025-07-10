package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.write.UserCommandMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService {
    
    private final UserCommandMapper userCommandMapper;
    
    public int createUser(User user) {
        return userCommandMapper.insertUser(user);
    }
    
    public int updateUser(User user) {
        return userCommandMapper.updateUser(user);
    }
    
    public int deleteUser(Integer userId) {
        return userCommandMapper.deleteUser(userId);
    }
    
    public int updateUserBirthDate(Integer userId, String birthDate) {
        return userCommandMapper.updateUserBirthDate(userId, birthDate);
    }
    
    public int updateLastLoginAt(Integer userId) {
        return userCommandMapper.updateLastLoginAt(userId);
    }
}