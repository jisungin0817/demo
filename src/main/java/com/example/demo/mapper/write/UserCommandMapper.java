package com.example.demo.mapper.write;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserCommandMapper {
    
    // 사용자 정보 수정 (생년월일)
    int updateUserBirthDate(@Param("userId") Integer userId, @Param("birthDate") String birthDate);
    
    // 사용자 삽입
    int insertUser(User user);
    
    // 사용자 완전 업데이트
    int updateUser(User user);
    
    // 사용자 삭제
    int deleteUser(@Param("userId") Integer userId);
    
    // 마지막 로그인 시간 업데이트
    int updateLastLoginAt(@Param("userId") Integer userId);
    
    //관련테이블 데이터 삭제
    int deleteMissionCompletionHistory(Integer userId);
    
    int goalDelete(Integer userId);
    
    int missionHisDelete(Integer userId);
    
    int chatHisDelete(Integer userId);
    
    int healthDelete(Integer userId);
}