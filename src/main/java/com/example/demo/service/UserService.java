package com.example.demo.service;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.read.UserQueryMapper;
import com.example.demo.mapper.write.UserCommandMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserQueryMapper userQueryMapper;
    private final UserCommandMapper userCommandMapper;

    @Transactional(readOnly = true)
    public UserProfileResponse getUserInfo(Integer userId) {
        log.info("사용자 정보 조회 요청 - user_id: {}", userId);
        
        User user = userQueryMapper.findByMemberSerialNumber(userId)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없습니다 - user_id: {}", userId);
                    return new RuntimeException("사용자를 찾을 수 없습니다");
                });
        
        log.info("사용자 정보 조회 성공 - user_id: {}", userId);
        return UserProfileResponse.builder()
                .memberSerialNumber(user.getMemberSerialNumber())
                .googleId(user.getGoogleId())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .occupation(user.getOccupation())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    @Transactional
    public UserProfileResponse updateUserBirthDate(Integer userId, String birthDate) {
        log.info("생년월일 수정 요청 - user_id: {}, birth_date: {}", userId, birthDate);
        
        // 1. 사용자 존재 확인 (Query 데이터소스에서 조회)
        User user = userQueryMapper.findByMemberSerialNumber(userId)
            .orElseThrow(() -> {
                log.error("사용자를 찾을 수 없습니다 - user_id: {}", userId);
                return new RuntimeException("사용자를 찾을 수 없습니다");
            });
        
        // 2. 생년월일 업데이트 (Command 데이터소스에서 수정)
        int updateResult = userCommandMapper.updateUserBirthDate(userId, birthDate);
        
        if (updateResult == 0) {
            log.error("생년월일 수정 실패 - user_id: {}", userId);
            throw new RuntimeException("생년월일 수정에 실패했습니다");
        }
        
        log.info("생년월일 수정 완료 - user_id: {}, 변경된 birth_date: {}", userId, birthDate);
        
        // 3. 수정된 사용자 정보 반환 (업데이트된 정보로 응답 생성)
        return UserProfileResponse.builder()
            .memberSerialNumber(user.getMemberSerialNumber())
            .googleId(user.getGoogleId())
            .name(user.getName())
            .birthDate(LocalDate.parse(birthDate)) // 수정된 생년월일
            .occupation(user.getOccupation())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt()) // 실제로는 DB에서 CURRENT_TIMESTAMP로 업데이트됨
            .lastLoginAt(user.getLastLoginAt())
            .build();
    }
    
    /**
     * 사용자 삭제
     * @param userId 사용자 ID
     */
    @Transactional
    public void deleteUser(Integer userId) {
        log.info("사용자 삭제 요청 - user_id: {}", userId);

        // 1. 사용자 존재 확인 (Query 데이터소스에서 조회)
        User user = userQueryMapper.findByMemberSerialNumber(userId)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없습니다 - user_id: {}", userId);
                    return new RuntimeException("사용자를 찾을 수 없습니다");
                });

        // 2. 관련 데이터 삭제 (Command 데이터소스에서 삭제)
        int deleteUserCount     = userCommandMapper.deleteUser(userId);
        int deleteGoalCount     = userCommandMapper.goalDelete(userId);
        int deleteMissionCount  = userCommandMapper.missionHisDelete(userId);
        int deleteChatCount     = userCommandMapper.chatHisDelete(userId);

        int deleteResult = deleteUserCount + deleteGoalCount + deleteMissionCount + deleteChatCount;

        // 삭제 실패 시 (주 테이블인 사용자 삭제가 실패했거나 모든 삭제가 0이면 실패로 간주)
        if (deleteUserCount == 0) {
            log.error("사용자 삭제 실패 - user_id: {}", userId);
            throw new RuntimeException("사용자 삭제에 실패했습니다");
        }

        log.info("사용자 삭제 완료 - user_id: {}, 삭제 건수: {}", userId, deleteResult);
    }
    
}