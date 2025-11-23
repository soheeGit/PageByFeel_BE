package org.pagebyfeel.exception.reading;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 독서 기록 도메인 관련 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ReadingErrorCode implements ErrorCode {
    
    // 독서 기록 조회 관련 (404 Not Found)
    READING_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "독서 기록을 찾을 수 없습니다."),
    
    // 독서 기록 유효성 검증 (400 Bad Request)
    INVALID_READING_PROGRESS(HttpStatus.BAD_REQUEST, "독서 진행률이 올바르지 않습니다."),
    INVALID_READING_DATE(HttpStatus.BAD_REQUEST, "독서 날짜가 올바르지 않습니다."),
    
    // 독서 기록 권한 관련 (403 Forbidden)
    READING_RECORD_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 독서 기록에 접근할 권한이 없습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
