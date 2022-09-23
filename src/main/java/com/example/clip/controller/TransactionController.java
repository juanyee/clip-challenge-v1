package com.example.clip.controller;


import javax.validation.Valid;
import com.example.clip.model.Payment;
import com.example.clip.request.PaymentRequest;
import com.example.clip.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/clip/transaction")
@AllArgsConstructor
public class TransactionController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentRequest paymentRequest) {

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setUserId(paymentRequest.getUserId());

        return new ResponseEntity<>(paymentService.create(payment), HttpStatus.OK);
    }
}
