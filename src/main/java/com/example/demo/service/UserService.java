package com.example.demo.service;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getUserInfo(Integer userId) {
        log.info("사용자 정보 조회 요청 - user_id: {}", userId);

        User user = userRepository.findByMemberSerialNumber(userId)
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

 // ✅ 생년월일 수정 메서드 (수정된 정보 반환하도록 변경)
    @Transactional
    public UserProfileResponse updateUserBirthDate(Integer userId, String birthDate) {
        log.info("생년월일 수정 요청 - user_id: {}, birth_date: {}", userId, birthDate);

        User user = userRepository.findByMemberSerialNumber(userId)
            .orElseThrow(() -> {
                log.error("사용자를 찾을 수 없습니다 - user_id: {}", userId);
                return new RuntimeException("사용자를 찾을 수 없습니다");
            });

        user.setBirthDate(LocalDate.parse(birthDate)); // yyyy-MM-dd 형식
        User savedUser = userRepository.save(user);

        log.info("생년월일 수정 완료 - user_id: {}, 변경된 birth_date: {}", userId, birthDate);
        
        // 수정된 사용자 정보 반환
        return UserProfileResponse.builder()
            .memberSerialNumber(savedUser.getMemberSerialNumber())
            .googleId(savedUser.getGoogleId())
            .name(savedUser.getName())
            .birthDate(savedUser.getBirthDate())
            .occupation(savedUser.getOccupation())
            .createdAt(savedUser.getCreatedAt())
            .updatedAt(savedUser.getUpdatedAt())
            .lastLoginAt(savedUser.getLastLoginAt())
            .build();
    }
}
