package org.pagebyfeel.exception.emotion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.pagebyfeel.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 감정 도메인 관련 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum EmotionErrorCode implements ErrorCode {
    
    // 감정 조회 관련 (404 Not Found)
    EMOTION_NOT_FOUND(HttpStatus.NOT_FOUND, "감정 기록을 찾을 수 없습니다."),
    
    // 감정 유효성 검증 (400 Bad Request)
    INVALID_EMOTION_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 감정 타입입니다."),
    INVALID_EMOTION_INTENSITY(HttpStatus.BAD_REQUEST, "감정 강도가 올바르지 않습니다."),
    EMOTION_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "감정 내용을 입력해주세요."),
    
    // 감정 권한 관련 (403 Forbidden)
    EMOTION_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 감정 기록에 접근할 권한이 없습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;
}
