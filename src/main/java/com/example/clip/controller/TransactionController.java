package com.example.clip.controller;


import javax.validation.Valid;

import com.example.clip.dto.TransactionResponseDTO;
import com.example.clip.model.Payment;
import com.example.clip.dto.PaymentRequestDTO;
import com.example.clip.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clip/transactions")
@AllArgsConstructor
public class TransactionController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody PaymentRequestDTO paymentRequest) {
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setUserId(paymentRequest.getUserId());
        return new ResponseEntity<>(new TransactionResponseDTO(paymentService.create(payment)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return new ResponseEntity<>(paymentService.getAll(), HttpStatus.OK);
    }
}
