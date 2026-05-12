package com.ecommerce.project.exception;

import com.ecommerce.project.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.InaccessibleObjectException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("404 Not_Found");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExistsException(UserAlreadyExistsException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("409 Conflict");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InaccessibleObjectException.class)
    public ResponseEntity<ErrorResponse> invalidCredentialsException(InvalidCredentialsException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("401 Unauthorized");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorResponse>emptyCartException(EmptyCartException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("400 Bad_Request");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse>unauthorizedException(UnauthorizedException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("403 Forbidden");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>methodArgumentNotValidException(MethodArgumentNotValidException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("400 Bad_Request");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>genericException(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("500 Internal_Server_Error");
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
