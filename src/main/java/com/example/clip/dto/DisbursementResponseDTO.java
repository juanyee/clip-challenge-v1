package com.example.clip.dto;

import com.example.clip.model.Payment;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author juan.yee
 */
@Getter
public class DisbursementResponseDTO {
    private final String userId;
    private final BigDecimal amount;

    public DisbursementResponseDTO(Payment payment) {
        this.userId = payment.getUserId();
        this.amount = payment.getAmount();
    }
}
