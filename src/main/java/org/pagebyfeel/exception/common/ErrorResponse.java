package org.pagebyfeel.exception.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("success")
    private boolean success = false;
    
    private String errorCode;      // 에러 코드 (예: USER_NOT_FOUND)
    private String message;        // 사용자 친화적 메시지
    private int status;            // HTTP 상태 코드
    private LocalDateTime timestamp;

    // 4개 파라미터 생성자 (기존 코드와의 호환성 유지)
    public ErrorResponse(String errorCode, String message, int status, LocalDateTime timestamp) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.name(),
                errorCode.getMessage(),
                errorCode.getHttpStatus().value(),
                LocalDateTime.now()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(
                errorCode.name(),
                message,
                errorCode.getHttpStatus().value(),
                LocalDateTime.now()
        );
    }
}
