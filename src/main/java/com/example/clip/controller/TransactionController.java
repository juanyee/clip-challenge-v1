package com.example.clip.controller;


import javax.validation.Valid;

import com.example.clip.dto.TransactionResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.model.Payment;
import com.example.clip.dto.PaymentRequestDTO;
import com.example.clip.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/clip/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Creates a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The resources were retrieved successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please check required parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource requested is not available",
                    content = @Content) })
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody PaymentRequestDTO paymentRequest) {
        if (paymentRequest == null
                || StringUtils.isBlank(paymentRequest.getUserId())
                || paymentRequest.getAmount() == null) {
            throw new ClipBadRequestException("payment request cannot be null or empty");
        }

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setUserId(paymentRequest.getUserId());
        return new ResponseEntity<>(transactionService.create(payment), HttpStatus.OK);
    }
}
