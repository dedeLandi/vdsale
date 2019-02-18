package vdsale.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vdsale.exceptions.DataNotFoundException;
import vdsale.exceptions.DefaultError;
import vdsale.exceptions.MethodNotImplementedException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DefaultError> validation(DataNotFoundException e, HttpServletRequest request) {
        log.error("M=ControllerExceptionHandler.DataNotFoundException.validation", e);
        DefaultError err = new DefaultError(HttpStatus.NOT_FOUND.value(),
                "Dados não encontrados", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MethodNotImplementedException.class)
    public ResponseEntity<DefaultError> validation(MethodNotImplementedException e, HttpServletRequest request) {
        log.error("M=ControllerExceptionHandler.MethodNotImplementedException.validation", e);
        DefaultError err = new DefaultError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Método não suportado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

}

