package com.example.clip.service;

import com.example.clip.dto.DisbursementResponseDTO;
import com.example.clip.exception.ClipNotFoundException;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class DisbursementService {

    private final PaymentRepository paymentRepository;

    /**
     * Generates disbursement for all transactions with status NEW and subtracts a fee of 3.5%
     * @return list of disbursed transactions
     */
    public synchronized List<DisbursementResponseDTO> getDisbursement() {
        List<Payment> payments = paymentRepository.findByStatus(PaymentStatus.NEW).stream()
                .map(item -> {
                    item.setAmount(item.getAmount()
                            .multiply(new BigDecimal("0.965"))
                            .setScale(2, RoundingMode.HALF_DOWN)); // simple way to subtract 3.5%
                    item.setStatus(PaymentStatus.PROCESSED);
                    item.setModificationDate(Calendar.getInstance());
                    return item;
                })
                .collect(Collectors.toList());

        paymentRepository.saveAll(payments);
        return payments.stream()
                .map(DisbursementResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Generates disbursement for a transactions based on the id with status NEW and subtracts a fee of 3.5%
     * @param id the transaction to be disbursed
     * @return the disbursed transaction
     */
    public DisbursementResponseDTO getDisbursementById(Long id) {
        Payment payment = paymentRepository.findByIdAndStatus(id, PaymentStatus.NEW);
        if (payment==null) {
            throw new ClipNotFoundException("id not found");
        }

        payment.setAmount(payment.getAmount()
                .multiply(new BigDecimal("0.965"))
                .setScale(2, RoundingMode.HALF_DOWN)); // simple way to subtract 3.5%
        payment.setStatus(PaymentStatus.PROCESSED);

        paymentRepository.save(payment);
        return new DisbursementResponseDTO(payment);
    }
}
