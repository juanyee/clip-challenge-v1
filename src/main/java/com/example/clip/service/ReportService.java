package com.example.clip.service;

import com.example.clip.dto.ReportResponseDTO;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class ReportService {
    private final PaymentRepository paymentRepository;


    /**
     * Returns a report of all users with the sum of all/new payments
     * @return the report of all users
     */
    public List<ReportResponseDTO> getReport() {
        // getting all payments
        List<Payment> allPayments = paymentRepository.findAll();

        // grouping by userId
        List<List<Payment>> lists = new ArrayList<>(
                allPayments.stream().collect(Collectors.groupingBy(Payment::getUserId)).values());

        // perform the regarding sums per userId
        return lists.stream()
                .map(payments -> getReportByPaymentList(payments.get(0).getUserId(), payments))
                .collect(Collectors.toList());
    }

    /**
     * Returns a report by UserId with the sum of all/new payments
     * @param userId UserId to be reported
     * @return the report per user
     */
    public ReportResponseDTO getReportByUserId(String userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return getReportByPaymentList(userId, payments);
    }

    private ReportResponseDTO getReportByPaymentList(String userId, List<Payment> payments) {
        ReportResponseDTO report = new ReportResponseDTO();
        report.setUserId(userId);
        report.setPaymentSum(payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        report.setNewPayments(payments.stream()
                .filter(item -> item.getStatus() == PaymentStatus.NEW)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        report.setNewPaymentsAmount(payments.stream()
                .filter(item -> item.getStatus() == PaymentStatus.NEW)
                .count());
        return report;
    }
}
