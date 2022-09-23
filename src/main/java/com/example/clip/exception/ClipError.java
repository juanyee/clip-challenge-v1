package com.example.clip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author juan.yee
 */
@Getter
@Setter
@AllArgsConstructor
public class ClipError {
    private LocalDateTime timeStamp;
    private HttpStatus statusCodeDescription;
    private int statusCode;
    private String errorMessage;
    private String path;
    private String debugMessage;
}
