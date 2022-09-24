package com.example.clip.service;

import com.example.clip.dto.DisbursementResponseDTO;
import com.example.clip.exception.ClipNotFoundException;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.repository.PaymentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class DisbursementServiceTest {
    @InjectMocks
    private DisbursementService disbursementService;
    @Mock
    private PaymentRepository paymentRepository;
    private final String userId = "User_1";
    private final Long id = 1L;

    @Test(expected = PersistenceException.class)
    public void testGetDisbursementWithPersistenceIssuesReturnsPersistenceException() {
        when(paymentRepository.findByStatus(PaymentStatus.NEW))
                .thenThrow(new PersistenceException("persistence exception"));

        disbursementService.getDisbursement();
    }

    @Test
    public void testGetDisbursementReturnsSuccessfulResponse() {
        Payment payment1 = new Payment();
        payment1.setId(id);
        payment1.setUserId(userId);
        payment1.setStatus(PaymentStatus.NEW);
        payment1.setAmount(new BigDecimal("100"));
        payment1.setCreationDate(Calendar.getInstance());
        payment1.setModificationDate(Calendar.getInstance());
        Payment payment2 = new Payment();
        payment2.setId(2);
        payment2.setUserId("User_2");
        payment2.setStatus(PaymentStatus.NEW);
        payment2.setAmount(new BigDecimal("200"));
        payment2.setCreationDate(Calendar.getInstance());
        payment2.setModificationDate(Calendar.getInstance());
        Payment payment3 = new Payment();
        payment3.setId(3);
        payment3.setUserId("User_3");
        payment3.setStatus(PaymentStatus.NEW);
        payment3.setAmount(new BigDecimal("300"));
        payment3.setCreationDate(Calendar.getInstance());
        payment3.setModificationDate(Calendar.getInstance());
        List<Payment> payments = List.of(payment1, payment2, payment3);

        Payment paymentModified1 = new Payment();
        paymentModified1.setUserId(userId);
        paymentModified1.setAmount(new BigDecimal("96.50"));
        Payment paymentModified2 = new Payment();
        paymentModified2.setUserId("User_2");
        paymentModified2.setAmount(new BigDecimal("193.00"));
        Payment paymentModified3 = new Payment();
        paymentModified3.setUserId("User_3");
        paymentModified3.setAmount(new BigDecimal("289.50"));
        List<DisbursementResponseDTO> disbursementResponse = List.of(
                new DisbursementResponseDTO(paymentModified1),
                new DisbursementResponseDTO(paymentModified2),
                new DisbursementResponseDTO(paymentModified3));

        when(paymentRepository.findByStatus(PaymentStatus.NEW)).thenReturn(payments);

        final List<DisbursementResponseDTO> result = disbursementService.getDisbursement();
        Assert.assertEquals(disbursementResponse.size(), result.size());
        Assert.assertEquals(disbursementResponse.get(0).getUserId(), result.get(0).getUserId());
        Assert.assertEquals(disbursementResponse.get(0).getAmount(), result.get(0).getAmount());
        Assert.assertEquals(disbursementResponse.get(1).getUserId(), result.get(1).getUserId());
        Assert.assertEquals(disbursementResponse.get(1).getAmount(), result.get(1).getAmount());
        Assert.assertEquals(disbursementResponse.get(2).getUserId(), result.get(2).getUserId());
        Assert.assertEquals(disbursementResponse.get(2).getAmount(), result.get(2).getAmount());
    }

    @Test(expected = PersistenceException.class)
    public void testGetDisbursementByIdWithPersistenceIssuesReturnsPersistenceException() {
        when(paymentRepository.findByIdAndStatus(id, PaymentStatus.NEW))
                .thenThrow(new PersistenceException("persistence exception"));

        disbursementService.getDisbursementById(id);
    }

    @Test(expected = ClipNotFoundException.class)
    public void testGetDisbursementByIdWithInvalidIdReturnsNotFoundException() {
        when(paymentRepository.findByIdAndStatus(id, PaymentStatus.NEW)).thenReturn(null);

        disbursementService.getDisbursementById(id);
    }

    @Test
    public void testGetDisbursementByIdSuccessfulResponse() {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setUserId(userId);
        payment.setStatus(PaymentStatus.NEW);
        payment.setAmount(new BigDecimal("100"));
        payment.setCreationDate(Calendar.getInstance());
        payment.setModificationDate(Calendar.getInstance());

        Payment paymentModified = new Payment();
        paymentModified.setUserId(userId);
        paymentModified.setAmount(new BigDecimal("96.50"));
        DisbursementResponseDTO disbursementResponse = new DisbursementResponseDTO(paymentModified);

        when(paymentRepository.findByIdAndStatus(id, PaymentStatus.NEW)).thenReturn(payment);

        final DisbursementResponseDTO result = disbursementService.getDisbursementById(id);
        Assert.assertEquals(disbursementResponse.getUserId(), result.getUserId());
        Assert.assertEquals(disbursementResponse.getAmount(), result.getAmount());
    }
}