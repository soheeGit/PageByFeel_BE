package org.pagebyfeel.exception.bookmark;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 북마크 도메인 관련 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum BookmarkErrorCode implements ErrorCode {
    
    // 북마크 조회 관련 (404 Not Found)
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "북마크를 찾을 수 없습니다."),
    
    // 북마크 중복 관련 (409 Conflict)
    BOOKMARK_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 북마크가 존재합니다."),
    
    // 북마크 유효성 검증 (400 Bad Request)
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "페이지 번호가 올바르지 않습니다."),
    
    // 북마크 권한 관련 (403 Forbidden)
    BOOKMARK_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 북마크에 접근할 권한이 없습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
