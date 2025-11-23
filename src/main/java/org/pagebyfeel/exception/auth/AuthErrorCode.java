package org.pagebyfeel.exception.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 인증/인가 관련 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    
    // 토큰 관련 (401 Unauthorized)
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    
    // Refresh Token 관련 (401 Unauthorized)
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),
    
    // 인증 관련 (401 Unauthorized)
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    CREDENTIALS_EXPIRED(HttpStatus.UNAUTHORIZED, "자격 증명이 만료되었습니다."),
    
    // 권한 관련 (403 Forbidden)
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "권한이 부족합니다."),
    ADMIN_ONLY(HttpStatus.FORBIDDEN, "관리자만 접근 가능합니다."),
    
    // OAuth 관련 (400 Bad Request / 401 Unauthorized)
    OAUTH_PROVIDER_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth 제공자입니다."),
    OAUTH_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "OAuth 로그인에 실패했습니다."),
    OAUTH_TOKEN_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "OAuth 토큰 요청에 실패했습니다."),
    OAUTH_USER_INFO_REQUEST_FAILED(HttpStatus.BAD_REQUEST, "OAuth 사용자 정보 요청에 실패했습니다."),
    OAUTH_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "OAuth 제공자에서 이메일을 찾을 수 없습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
