package com.roomify.exceptionHandler;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String msg) {
        super(msg);
    }
}