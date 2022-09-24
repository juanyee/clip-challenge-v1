package com.example.clip.controller;

import com.example.clip.dto.DisbursementResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.exception.ClipNotFoundException;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.service.DisbursementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class DisbursementControllerTest {
    @InjectMocks
    private DisbursementController disbursementController;
    @Mock
    private DisbursementService disbursementService;
    private final Long id = 1L;

    @Test
    public void testGetDisbursementReturnsSuccessfulResponse() {
        DisbursementResponseDTO disResponse1 = mock(DisbursementResponseDTO.class);
        DisbursementResponseDTO disResponse2 = mock(DisbursementResponseDTO.class);
        DisbursementResponseDTO disResponse3 = mock(DisbursementResponseDTO.class);
        List<DisbursementResponseDTO> disbursementResponse = List.of(disResponse1, disResponse2, disResponse3);

        when(disbursementService.getDisbursement()).thenReturn(disbursementResponse);

        final ResponseEntity<?> result = disbursementController.getDisbursement();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(disbursementResponse, result.getBody());
    }

    @Test(expected = ClipBadRequestException.class)
    public void testGetDisbursementByIdWithInvalidIdReturnsBadRequestException() {
        disbursementController.getDisbursementById(null);
    }

    @Test(expected = ClipNotFoundException.class)
    public void testGetDisbursementByIdWithInvalidIdReturnsNotFoundException() {
        when(disbursementService.getDisbursementById(id))
                .thenThrow(new ClipNotFoundException("not found exception"));
        disbursementController.getDisbursementById(id);
    }

    @Test
    public void testGetDisbursementByIdReturnsSuccessfulResponse() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setUserId("User_1");
        payment.setAmount(new BigDecimal("96.50"));
        payment.setStatus(PaymentStatus.PROCESSED);
        payment.setCreationDate(Calendar.getInstance());
        payment.setModificationDate(Calendar.getInstance());
        DisbursementResponseDTO disbursementResponse = new DisbursementResponseDTO(payment);

        when(disbursementService.getDisbursementById(any())).thenReturn(disbursementResponse);

        final ResponseEntity<?> result = disbursementController.getDisbursementById(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(disbursementResponse, result.getBody());
    }
}