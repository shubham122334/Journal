package net.engineeringdigest.journalApp.exception;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ExResponse {
    private LocalDateTime timestamp;
    private  String message;
    private int errorCode;
    private HttpStatus status;

}
