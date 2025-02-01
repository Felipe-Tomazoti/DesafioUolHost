package br.com.FelipeTomazoti.desafioUOL.controller.exception;

import br.com.FelipeTomazoti.desafioUOL.service.exception.NoCodenamesAvailable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoCodenamesAvailable.class)
    public ResponseEntity<StandardError> noCodenamesAvailable(NoCodenamesAvailable ex, HttpServletRequest request) {
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NO_CONTENT.value(), ex.getMessage(),
                "no codenames available", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
