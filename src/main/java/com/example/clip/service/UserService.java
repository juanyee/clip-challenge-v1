package com.example.clip.service;

import com.example.clip.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author juan.yee
 */
@Service
@AllArgsConstructor
public class UserService {

    private final PaymentRepository paymentRepository;

    public List<String> getAllDistinctUsers() {
        return paymentRepository.findAllDistinctUsers();
    }

}
