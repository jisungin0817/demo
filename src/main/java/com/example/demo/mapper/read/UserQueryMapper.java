package com.example.demo.mapper.read;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserQueryMapper {
    
    // 사용자 정보 조회 (기본키로)
    Optional<User> findById(@Param("id") Integer id);
    
    // 사용자 정보 조회 (회원 일련번호로)
    Optional<User> findByMemberSerialNumber(@Param("memberSerialNumber") Integer memberSerialNumber);
    
    // 모든 사용자 조회
    List<User> findAll();
    
    // 사용자 수 조회
    long count();
    
    // 구글 ID로 사용자 조회
    Optional<User> findByGoogleId(@Param("googleId") String googleId);
    
    // 이름으로 사용자 조회
    List<User> findByName(@Param("name") String name);
}