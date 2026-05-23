package com.roomify.exceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.roomify.response.ResponseApi;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 Handle RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseApi<Object>> handleRuntimeException(RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseApi.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // 🔴 Handle IllegalArgument (ENUM / valueOf errors)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseApi<Object>> handleIllegalArgument(IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseApi.builder()
                        .success(false)
                        .message("Invalid input: " + ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    // 🔴 Handle File Upload Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<Object>> handleGenericException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseApi.builder()
                        .success(false)
                        .message("Something went wrong!")
                        .data(ex.getMessage())  // debug purpose
                        .timestamp(LocalDateTime.now())
                        .build());
    }
    
    @ExceptionHandler(RoomNotAvailableException.class)
    public String handleRoomNotAvailable(RoomNotAvailableException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "viewHotel"; // custom UI page
    }
}