package com.example.clip.controller;

import com.example.clip.dto.PaymentRequestDTO;
import com.example.clip.dto.TransactionResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    @InjectMocks
    private TransactionController transactionController;
    @Mock
    private TransactionService transactionService;
    private final String userId = "User_1";
    private final BigDecimal amount = new BigDecimal("100");

    @Test(expected = ClipBadRequestException.class)
    public void testCreateWithInvalidRequestBodyReturnsBadRequestException() {
        transactionController.create(null);
    }

    @Test
    public void testCreateReturnsSuccessfulResponse() {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO(userId, amount);
        Payment paymentResponse = new Payment();
        paymentResponse.setId(1L);
        paymentResponse.setUserId(userId);
        paymentResponse.setAmount(amount);
        paymentResponse.setStatus(PaymentStatus.NEW);
        paymentResponse.setCreationDate(Calendar.getInstance());
        paymentResponse.setModificationDate(Calendar.getInstance());
        TransactionResponseDTO transactionResponse = new TransactionResponseDTO(paymentResponse);

        when(transactionService.create(any())).thenReturn(transactionResponse);

        final ResponseEntity<?> result = transactionController.create(paymentRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(transactionResponse, result.getBody());
    }
}