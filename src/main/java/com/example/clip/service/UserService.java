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

    /**
     * Returns the list of all the different users saved in the payment table
     * @return the list of users
     */
    public List<String> getAllDistinctUsers() {
        return paymentRepository.findAllDistinctUsers();
    }

}
