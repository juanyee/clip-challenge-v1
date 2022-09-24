package com.example.clip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author juan.yee
 */
@AllArgsConstructor
@Getter
public enum PaymentStatus {

    NEW("NEW"),
    PROCESSED("PROCESSED");

    private final String value;
}
