package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.read.UserQueryMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {
    
    private final UserQueryMapper userQueryMapper;
    
    public Optional<User> findById(Integer id) {
        return userQueryMapper.findById(id);
    }
    
    public List<User> findAll() {
        return userQueryMapper.findAll();
    }
    
    public List<User> findByName(String name) {
        return userQueryMapper.findByName(name);
    }
    
    public Optional<User> findByGoogleId(String googleId) {
        return userQueryMapper.findByGoogleId(googleId);
    }
    
    public Optional<User> findByMemberSerialNumber(Integer memberSerialNumber) {
        return userQueryMapper.findByMemberSerialNumber(memberSerialNumber);
    }
    
    public long count() {
        return userQueryMapper.count();
    }
}