package com.example.clip.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestDTO {
    @NotNull
    String userId;
    @NotNull
    BigDecimal amount;
}
