package com.ffutagawa.demo_park_api.web.exception;

import com.ffutagawa.demo_park_api.exception.EntityNotFoundException;
import com.ffutagawa.demo_park_api.exception.PasswordInvalidException;
import com.ffutagawa.demo_park_api.exception.UserNameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroMessage> ArgumentNotValidException (MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result){
        log.error("Aoi Error - ", ex);

        return ResponseEntity
                .status(HttpStatus.UPGRADE_REQUIRED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", result));

    }

    @ExceptionHandler(UserNameUniqueViolationException.class)
    public ResponseEntity<ErroMessage> uniqueViolationException (RuntimeException ex,
                                                                        HttpServletRequest request){
        log.error("Aoi Error - ", ex);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.CONFLICT, ex.getMessage()));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroMessage> entityNotFoundException (RuntimeException ex,
                                                                         HttpServletRequest request){
        log.error("Aoi Error - ", ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErroMessage> passwordInvalidException (RuntimeException ex,
                                                                HttpServletRequest request){
        log.error("Aoi Error - ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));

    }
























}
