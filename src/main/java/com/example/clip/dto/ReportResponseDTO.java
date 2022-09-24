package com.example.clip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author juan.yee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportResponseDTO {
    @JsonProperty("user_name")
    private String userId;
    @JsonProperty("payments_sum")
    private BigDecimal paymentSum;
    @JsonProperty("new_payments")
    private BigDecimal newPayments;
    @JsonProperty("new_payments_amount")
    private Long newPaymentsAmount;
}
