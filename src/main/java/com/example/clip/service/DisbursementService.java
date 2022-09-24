package com.example.clip.service;

import com.example.clip.dto.DisbursementResponseDTO;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class DisbursementService {

    private final PaymentRepository paymentRepository;

    public List<DisbursementResponseDTO> getDisbursement() {
        List<Payment> payments = paymentRepository.findByStatus(PaymentStatus.NEW).stream()
                .map(item -> {
                    item.setAmount(item.getAmount()
                            .multiply(new BigDecimal("0.965"))
                            .setScale(2, RoundingMode.HALF_DOWN)); // simple way to subtract 3.5%
                    item.setStatus(PaymentStatus.PROCESSED);
                    return item;
                })
                .collect(Collectors.toList());

        paymentRepository.saveAll(payments);
        return payments.stream()
                .map(DisbursementResponseDTO::new)
                .collect(Collectors.toList());
    }

    public DisbursementResponseDTO getDisbursementById(Long id) {
        Payment payment = paymentRepository.findByIdAndStatus(id, PaymentStatus.NEW);
        payment.setAmount(payment.getAmount()
                .multiply(new BigDecimal("0.965"))
                .setScale(2, RoundingMode.HALF_DOWN)); // simple way to subtract 3.5%
        payment.setStatus(PaymentStatus.PROCESSED);

        paymentRepository.save(payment);
        return new DisbursementResponseDTO(payment);
    }
}
