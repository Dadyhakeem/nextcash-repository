package com.dev.hakeem.nextcash.web.exception;

import com.dev.hakeem.nextcash.exception.CpfUniqueViolationExeption;
import com.dev.hakeem.nextcash.exception.EmailUniqueViolationExeption;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.exception.PasswordInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroMessage> MethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                       HttpServletRequest request,
                                                                       BindingResult result){
        log.error("api Error - ",ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request,HttpStatus.UNPROCESSABLE_ENTITY, "Campos invalido(s)", result));

    }



    @ExceptionHandler({EmailUniqueViolationExeption.class ,CpfUniqueViolationExeption.class})
    public ResponseEntity<ErroMessage> EmailUniqueViolationExeption(RuntimeException ex,
                                                                       HttpServletRequest request
                                                                       ){
        log.error("api Error - ",ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request,HttpStatus.CONFLICT, ex.getMessage()));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroMessage> EntityNotFoundException(RuntimeException ex,
                                                                    HttpServletRequest request
    ){
        log.error("api Error - ",ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErroMessage> PasswordInvalidException(RuntimeException ex,
                                                               HttpServletRequest request
    ){
        log.error("api Error - ",ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request,HttpStatus.BAD_REQUEST, ex.getMessage()));

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroMessage> AccessDeniedException(AccessDeniedException ex,
                                                                HttpServletRequest request
    ){
        log.error("api Error - ",ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request,HttpStatus.FORBIDDEN, ex.getMessage()));

    }
}
