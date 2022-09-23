package com.example.clip.service;

import com.example.clip.model.Payment;
import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }
}
