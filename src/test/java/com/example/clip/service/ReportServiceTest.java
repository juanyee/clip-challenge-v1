package com.example.clip.service;

import com.example.clip.dto.ReportResponseDTO;
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
public class ReportServiceTest {
    @InjectMocks
    private ReportService reportService;
    @Mock
    private PaymentRepository paymentRepository;
    private final String userId = "User_1";

    @Test(expected = PersistenceException.class)
    public void testGetReportWithPersistenceIssuesReturnsPersistenceException() {
        when(paymentRepository.findAll())
                .thenThrow(new PersistenceException("persistence exception"));

        reportService.getReport();
    }

    @Test
    public void testGetReportReturnsSuccessfulResponse() {
        Payment payment1 = new Payment();
        payment1.setId(1);
        payment1.setUserId(userId);
        payment1.setAmount(new BigDecimal("96.50"));
        payment1.setStatus(PaymentStatus.PROCESSED);
        payment1.setCreationDate(Calendar.getInstance());
        payment1.setModificationDate(Calendar.getInstance());
        Payment payment2 = new Payment();
        payment2.setId(1);
        payment2.setUserId(userId);
        payment2.setAmount(new BigDecimal("100.00"));
        payment2.setStatus(PaymentStatus.NEW);
        payment2.setCreationDate(Calendar.getInstance());
        payment2.setModificationDate(Calendar.getInstance());
        Payment payment3 = new Payment();
        payment3.setId(1);
        payment3.setUserId(userId);
        payment3.setAmount(new BigDecimal("200.00"));
        payment3.setStatus(PaymentStatus.NEW);
        payment3.setCreationDate(Calendar.getInstance());
        payment3.setModificationDate(Calendar.getInstance());
        List<Payment> allPayments = List.of(payment1, payment2, payment3);

        ReportResponseDTO report1 = new ReportResponseDTO();
        report1.setUserId(userId);
        report1.setPaymentSum(new BigDecimal("396.50"));
        report1.setNewPayments(new BigDecimal("300.00"));
        report1.setNewPaymentsAmount(2L);
        List<ReportResponseDTO> reportResponse = List.of(report1);

        when(paymentRepository.findAll()).thenReturn(allPayments);

        final List<ReportResponseDTO> result = reportService.getReport();
        Assert.assertEquals(reportResponse.size(), result.size());
        Assert.assertEquals(reportResponse.get(0).getUserId(), result.get(0).getUserId());
        Assert.assertEquals(reportResponse.get(0).getPaymentSum(), result.get(0).getPaymentSum());
        Assert.assertEquals(reportResponse.get(0).getNewPayments(), result.get(0).getNewPayments());
        Assert.assertEquals(reportResponse.get(0).getNewPaymentsAmount(), result.get(0).getNewPaymentsAmount());
    }

    @Test(expected = PersistenceException.class)
    public void testGetReportByUserIdWithPersistenceIssuesReturnsPersistenceException() {
        when(paymentRepository.findByUserId(userId))
                .thenThrow(new PersistenceException("persistence exception"));

        reportService.getReportByUserId(userId);
    }

    @Test(expected = ClipNotFoundException.class)
    public void testGetReportByUserIdWithInvalidUserIdReturnsNotFoundException() {
        when(paymentRepository.findByUserId(userId)).thenReturn(null);

        reportService.getReportByUserId(userId);
    }

    @Test
    public void testGetReportByUserIdReturnsSuccessfulResponse() {
        Payment payment1 = new Payment();
        payment1.setId(1);
        payment1.setUserId(userId);
        payment1.setAmount(new BigDecimal("96.50"));
        payment1.setStatus(PaymentStatus.PROCESSED);
        payment1.setCreationDate(Calendar.getInstance());
        payment1.setModificationDate(Calendar.getInstance());
        Payment payment2 = new Payment();
        payment2.setId(1);
        payment2.setUserId(userId);
        payment2.setAmount(new BigDecimal("100.00"));
        payment2.setStatus(PaymentStatus.NEW);
        payment2.setCreationDate(Calendar.getInstance());
        payment2.setModificationDate(Calendar.getInstance());
        Payment payment3 = new Payment();
        payment3.setId(1);
        payment3.setUserId(userId);
        payment3.setAmount(new BigDecimal("200.00"));
        payment3.setStatus(PaymentStatus.NEW);
        payment3.setCreationDate(Calendar.getInstance());
        payment3.setModificationDate(Calendar.getInstance());
        List<Payment> userPayments = List.of(payment1, payment2, payment3);

        ReportResponseDTO reportResponse = new ReportResponseDTO();
        reportResponse.setUserId(userId);
        reportResponse.setPaymentSum(new BigDecimal("396.50"));
        reportResponse.setNewPayments(new BigDecimal("300.00"));
        reportResponse.setNewPaymentsAmount(2L);

        when(paymentRepository.findByUserId(userId)).thenReturn(userPayments);

        final ReportResponseDTO result = reportService.getReportByUserId(userId);
        Assert.assertEquals(reportResponse.getUserId(), result.getUserId());
        Assert.assertEquals(reportResponse.getPaymentSum(), result.getPaymentSum());
        Assert.assertEquals(reportResponse.getNewPayments(), result.getNewPayments());
        Assert.assertEquals(reportResponse.getNewPaymentsAmount(), result.getNewPaymentsAmount());
    }
}