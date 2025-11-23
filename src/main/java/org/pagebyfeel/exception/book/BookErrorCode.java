package org.pagebyfeel.exception.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 책 도메인 관련 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum BookErrorCode implements ErrorCode {
    
    // 책 조회 관련 (404 Not Found)
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "책을 찾을 수 없습니다."),
    
    // 책 중복 관련 (409 Conflict)
    BOOK_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 책입니다."),
    
    // 책 유효성 검증 (400 Bad Request)
    INVALID_BOOK_TITLE(HttpStatus.BAD_REQUEST, "책 제목이 올바르지 않습니다."),
    INVALID_BOOK_AUTHOR(HttpStatus.BAD_REQUEST, "저자 정보가 올바르지 않습니다."),
    INVALID_ISBN(HttpStatus.BAD_REQUEST, "ISBN이 올바르지 않습니다."),
    
    // 책 권한 관련 (403 Forbidden)
    BOOK_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 책에 접근할 권한이 없습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
