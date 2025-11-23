package org.pagebyfeel.exception.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 전역 에러 코드
 * 특정 도메인에 속하지 않는 일반적인 에러
 */
@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    
    // 서버 에러 (500 Internal Server Error)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 에러가 발생했습니다."),
    
    // 외부 API 에러 (502 Bad Gateway)
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 호출 중 에러가 발생했습니다."),
    
    // 입력 검증 (400 Bad Request)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    MISSING_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "필수 입력 항목이 누락되었습니다."),
    
    // 리소스 관련 (404 Not Found)
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    
    // 메서드 관련 (405 Method Not Allowed)
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
