package it.sara.demo.exception;

import it.sara.demo.dto.StatusDTO;
import lombok.Getter;

@Getter
public class GenericException extends Exception {

    private final StatusDTO status;

    public GenericException() {
        this.status = StatusDTO.builder()
                .code(500)
                .message("Generic error")
                .traceId(null)
                .build();
    }

}
