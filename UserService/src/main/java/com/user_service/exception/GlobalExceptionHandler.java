package com.user_service.exception;

import com.user_service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException ex )
    {
      ApiResponse apiResponse=new ApiResponse(ex.getMessage(),false);
      return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
