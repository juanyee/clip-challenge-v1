package com.example.clip.controller;

import com.example.clip.dto.ReportResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.exception.ClipNotFoundException;
import com.example.clip.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {
    @InjectMocks
    private ReportController reportController;
    @Mock
    private ReportService reportService;
    private final String userId = "User_1";

    @Test
    public void testGetReportReturnsSuccessfulResponse() {
        ReportResponseDTO repResponse1 = mock(ReportResponseDTO.class);
        ReportResponseDTO repResponse2 = mock(ReportResponseDTO.class);
        ReportResponseDTO repResponse3 = mock(ReportResponseDTO.class);
        List<ReportResponseDTO> reportResponse = List.of(repResponse1, repResponse2, repResponse3);

        when(reportService.getReport()).thenReturn(reportResponse);

        final ResponseEntity<?> result = reportController.getReport();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reportResponse, result.getBody());
    }

    @Test(expected = ClipBadRequestException.class)
    public void testGetReportByUserIdWithInvalidIdReturnsBadRequestException() {
        reportController.getReportByUserId(null);
    }

    @Test(expected = ClipNotFoundException.class)
    public void testGetReportByUserIdWithInvalidIdReturnsNotFoundException() {
        when(reportService.getReportByUserId(userId))
                .thenThrow(new ClipNotFoundException("not found exception"));
        reportController.getReportByUserId(userId);
    }

    @Test
    public void testGetReportByUserIdReturnsSuccessfulResponse() {
        ReportResponseDTO reportResponse = new ReportResponseDTO();
        reportResponse.setUserId(userId);
        reportResponse.setPaymentSum(new BigDecimal("1.00"));
        reportResponse.setNewPayments(new BigDecimal("2.00"));
        reportResponse.setNewPaymentsAmount(3L);

        when(reportService.getReportByUserId(any())).thenReturn(reportResponse);

        final ResponseEntity<?> result = reportController.getReportByUserId(userId);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reportResponse, result.getBody());
    }
}