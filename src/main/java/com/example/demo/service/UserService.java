package com.example.demo.service;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}