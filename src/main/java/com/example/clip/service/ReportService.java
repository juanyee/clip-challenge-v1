package com.example.clip.service;

import com.example.clip.dto.ReportResponseDTO;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class ReportService {
    private final PaymentRepository paymentRepository;

    /**
     * Returns a report by UserId with the sum of all/new payments
     * @param userId UserId to be reported
     * @return the report
     */
    public ReportResponseDTO getReportByUserId(String userId) {
        List<Payment> allPayments = paymentRepository.findByUserId(userId);

        ReportResponseDTO report = new ReportResponseDTO();
        report.setUserId(userId);
        report.setPaymentSum(allPayments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        report.setNewPayments(allPayments.stream()
                .filter(item -> item.getStatus() == PaymentStatus.NEW)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        report.setNewPaymentsAmount(allPayments.stream()
                .filter(item -> item.getStatus() == PaymentStatus.NEW)
                .count());
        return report;
    }
}
