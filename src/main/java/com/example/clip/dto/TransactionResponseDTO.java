package com.example.clip.dto;

import com.example.clip.model.Payment;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author juan.yee
 */
@Getter
public class TransactionResponseDTO {

    private final long id;
    private final String userId;
    private final BigDecimal amount;

    public TransactionResponseDTO(Payment payment) {
        this.id = payment.getId();
        this.userId = payment.getUserId();
        this.amount = payment.getAmount();
    }
}
