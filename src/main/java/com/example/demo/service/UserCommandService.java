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
    
    /**
     * 사용자 목표 데이터만 삭제
     * @param userId 사용자 ID
     * @return 삭제된 행 수
     */
    public int deleteUserGoals(Integer userId) {
        return userCommandMapper.goalDelete(userId);
    }
    
    /**
     * 사용자 미션 이력 데이터만 삭제
     * @param userId 사용자 ID
     * @return 삭제된 행 수
     */
    public int deleteUserMissionHistory(Integer userId) {
        return userCommandMapper.missionHisDelete(userId);
    }
    
    /**
     * 사용자 채팅 이력 데이터만 삭제
     * @param userId 사용자 ID
     * @return 삭제된 행 수
     */
    public int deleteUserChatHistory(Integer userId) {
        return userCommandMapper.chatHisDelete(userId);
    }
    
}