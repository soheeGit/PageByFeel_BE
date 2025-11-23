package org.pagebyfeel.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    
    // 사용자 조회/존재 관련 (404 Not Found)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    
    // 사용자 중복 관련 (409 Conflict)
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    
    // 비밀번호 관련 (400 Bad Request)
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    WEAK_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 강도가 약합니다."),
    
    // 계정 상태 관련 (403 Forbidden)
    ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "비활성화된 계정입니다."),
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "잠긴 계정입니다."),
    ACCOUNT_DELETED(HttpStatus.GONE, "삭제된 계정입니다."),
    ACCOUNT_SUSPENDED(HttpStatus.FORBIDDEN, "정지된 계정입니다."),
    
    // 프로필 관련 (400 Bad Request)
    INVALID_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, "유효하지 않은 프로필 이미지입니다."),
    PROFILE_IMAGE_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "프로필 이미지 크기가 너무 큽니다."),
    INVALID_NICKNAME_FORMAT(HttpStatus.BAD_REQUEST, "닉네임 형식이 올바르지 않습니다."),
    NICKNAME_TOO_SHORT(HttpStatus.BAD_REQUEST, "닉네임이 너무 짧습니다."),
    NICKNAME_TOO_LONG(HttpStatus.BAD_REQUEST, "닉네임이 너무 깁니다."),
    
    // 검증 관련 (400 Bad Request)
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "필수 입력 항목이 누락되었습니다."),
    INVALID_USER_INPUT(HttpStatus.BAD_REQUEST, "유효하지 않은 입력값입니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
