package com.example.clip.service;

import com.example.clip.dto.TransactionResponseDTO;
import com.example.clip.model.Payment;
import com.example.clip.repository.PaymentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.PersistenceException;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private PaymentRepository paymentRepository;
    private final String userId = "User_1";

    @Test(expected = PersistenceException.class)
    public void testCreateWithPersistenceIssuesReturnsPersistenceException() {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(new BigDecimal("100.00"));

        when(paymentRepository.save(payment))
                .thenThrow(new PersistenceException("persistence exception"));

        transactionService.create(payment);
    }

    @Test
    public void testCreateReturnsSuccessfulResponse() {
        Payment paymentRequest = new Payment();
        paymentRequest.setUserId(userId);
        paymentRequest.setAmount(new BigDecimal("100.00"));

        Payment paymentResponse = new Payment();
        paymentResponse.setId(1L);
        paymentResponse.setUserId(userId);
        paymentResponse.setAmount(new BigDecimal("100.00"));
        TransactionResponseDTO transactionResponse = new TransactionResponseDTO(paymentResponse);

        when(paymentRepository.save(paymentRequest)).thenReturn(paymentResponse);

        final TransactionResponseDTO result = transactionService.create(paymentRequest);
        Assert.assertEquals(transactionResponse.getId(), result.getId());
        Assert.assertEquals(transactionResponse.getUserId(), result.getUserId());
        Assert.assertEquals(transactionResponse.getAmount(), result.getAmount());
    }
}