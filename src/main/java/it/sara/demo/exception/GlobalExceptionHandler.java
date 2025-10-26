package it.sara.demo.exception;

import it.sara.demo.dto.StatusDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<StatusDTO> handleGeneric(GenericException ex, HttpServletRequest request) {
        StatusDTO status = ex.getStatus();
        if (status.getTraceId() == null)
            status.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<StatusDTO> handleDatabase(DatabaseOperationException ex, HttpServletRequest request) {
        StatusDTO status = ex.getStatus();
        if (status.getTraceId() == null)
            status.setTraceId(UUID.randomUUID().toString());
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StatusDTO> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        StatusDTO status = new StatusDTO();
        status.setCode(HttpStatus.BAD_REQUEST.value());
        status.setMessage(message);
        status.setTraceId(UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusDTO> handleOther(Exception ex) {
        StatusDTO status = new StatusDTO();
        status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        status.setMessage("Errore generico durante l'esecuzione");
        status.setTraceId(UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

}
