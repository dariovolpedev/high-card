package it.sara.demo.exception;

import it.sara.demo.dto.StatusDTO;
import lombok.Getter;

@Getter
public class DatabaseOperationException extends Exception {

    private final StatusDTO status;

    public DatabaseOperationException() {
        this.status = StatusDTO.builder()
                .code(500)
                .message("Database operation failed")
                .traceId(null)
                .build();
    }

    public DatabaseOperationException(String message) {
        this.status = StatusDTO.builder()
                .code(500)
                .message(message)
                .traceId(null)
                .build();
    }

}
