package com.example.clip.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    @NotNull
    String userId;
    @NotNull
    BigDecimal amount;
}
