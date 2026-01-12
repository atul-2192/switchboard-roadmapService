package com.Roadmap_Service.Roadmap.Service.Exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

