package com.roomify.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseApi<T> {

    private String token;
    private String message;
    private boolean success;
    private T data;
    private LocalDateTime timestamp;
}
