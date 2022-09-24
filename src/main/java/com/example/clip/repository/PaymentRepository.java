package com.example.clip.repository;

import com.example.clip.model.Payment;
import com.example.clip.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select distinct userId from Payment")
    List<String> findAllDistinctUsers();

    List<Payment> findByStatus(PaymentStatus status);

    Payment findByIdAndStatus(Long id, PaymentStatus status);
}
