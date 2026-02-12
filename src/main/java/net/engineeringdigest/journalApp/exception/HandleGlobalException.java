package net.engineeringdigest.journalApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class HandleGlobalException {

    @ExceptionHandler(JournalEntryNotFoundException.class)
    public ResponseEntity<ExResponse> handleJournalException(JournalEntryNotFoundException ex) {
        ExResponse response=ExResponse.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExResponse> handleUserException(UserException ex) {
        ExResponse response=ExResponse.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ExResponse response=ExResponse.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}
