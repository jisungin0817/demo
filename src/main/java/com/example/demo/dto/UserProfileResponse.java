package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 프로필 응답")
public class UserProfileResponse {

    @JsonProperty("member_serial_number")
    @Schema(description = "사용자 ID", example = "1")
    private Integer memberSerialNumber;

    @JsonProperty("google_id")
    @Schema(description = "구글 ID", example = "string")
    private String googleId;

    @JsonProperty("name")
    @Schema(description = "사용자 이름", example = "string")
    private String name;

    @JsonProperty("birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "생년월일", example = "2025-07-07")
    private LocalDate birthDate;

    @JsonProperty("occupation")
    @Schema(description = "직업", example = "string")
    private String occupation;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Schema(description = "생성일시", example = "2025-07-07T04:53:44.039Z")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Schema(description = "수정일시", example = "2025-07-07T04:53:44.039Z")
    private LocalDateTime updatedAt;

    @JsonProperty("last_login_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Schema(description = "마지막 로그인 시간", example = "2025-07-07T04:53:44.039Z")
    private LocalDateTime lastLoginAt;
}