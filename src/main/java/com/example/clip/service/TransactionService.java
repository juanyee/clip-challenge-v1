package com.example.clip.service;

import com.example.clip.dto.TransactionResponseDTO;
import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class TransactionService {
    private final PaymentRepository paymentRepository;

    /**
     * Create a new payment
     * @param payment payment to be created
     * @return the created payment
     */
    public TransactionResponseDTO create(Payment payment) {
        payment.setStatus(PaymentStatus.NEW);
        payment.setCreationDate(Calendar.getInstance());
        payment.setModificationDate(Calendar.getInstance());
        return new TransactionResponseDTO(paymentRepository.save(payment));
    }
}
