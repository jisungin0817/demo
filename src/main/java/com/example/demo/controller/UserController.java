package com.example.demo.controller;

import com.example.demo.dto.UserProfileResponse;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "User Management", description = "사용자 정보 조회 및 관리")
public class UserController {

    private final UserService userService;

    @GetMapping("/userInfo")
    
    //swagger를위함
    @Operation(
        summary = "👤 사용자 정보 조회",
        description = """
            사용자 ID로 사용자 정보를 조회합니다.
            
            **처리 과정:**
            1. 사용자 ID로 데이터베이스에서 사용자 정보 조회
            2. 사용자 기본 정보 반환 (이름, 생년월일, 직업 등)
            
            **응답 정보:**
            - 회원 일련번호
            - 구글 ID
            - 이름
            - 생년월일
            - 직업
            - 생성일시
            - 수정일시
            - 마지막 로그인 시간
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful Response",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProfileResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "사용자를 찾을 수 없습니다"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
        )
    })
    public ResponseEntity<UserProfileResponse> getUserInfo(
        @Parameter(description = "사용자 ID", required = true, example = "1")
        @RequestParam("user_id") 
        @Min(value = 1, message = "사용자 ID는 1 이상이어야 합니다") 
        Integer userId
    ) {
        log.info("사용자 정보 조회 API 호출 - user_id: {}", userId);
        
        try {
            UserProfileResponse response = userService.getUserInfo(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("사용자 정보 조회 실패 - user_id: {}, error: {}", userId, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("사용자 정보 조회 중 예상치 못한 오류 발생 - user_id: {}", userId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    @PutMapping("/userUpdate")
    @Operation(
        summary = "👤 사용자 생년월일 수정",
        description = """
        사용자의 생년월일을 수정합니다.
        
        **처리 과정:**
        1. 사용자 ID로 기존 사용자 정보 확인
        2. 생년월일 업데이트
        3. 수정된 사용자 정보 반환
        
        **수정 가능한 정보:**
        - 생년월일 (yyyy-MM-dd 형식)
        """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProfileResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "사용자를 찾을 수 없습니다"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터"
        )
    })
    public ResponseEntity<UserProfileResponse> updateUser(
        @Parameter(description = "사용자 정보 수정 요청", required = true)
        @Valid @RequestBody UserUpdateRequest updateRequest
    ) {
        log.info("사용자 생년월일 수정 API 호출 - user_id: {}, birth_date: {}", 
                 updateRequest.getUserId(), updateRequest.getBirthDate());
        
        try {
            UserProfileResponse response = userService.updateUserBirthDate(
                updateRequest.getUserId(), 
                updateRequest.getBirthDate()
            );
            log.info("사용자 생년월일 수정 성공 - user_id: {}", updateRequest.getUserId());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("사용자 생년월일 수정 실패 - user_id: {}, error: {}", 
                      updateRequest.getUserId(), e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("사용자 생년월일 수정 중 예상치 못한 오류 발생 - user_id: {}", 
                      updateRequest.getUserId(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}