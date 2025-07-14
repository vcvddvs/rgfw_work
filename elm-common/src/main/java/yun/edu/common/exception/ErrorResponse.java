package yun.edu.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
} 