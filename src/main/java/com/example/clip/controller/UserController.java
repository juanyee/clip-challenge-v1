package com.example.clip.controller;

import com.example.clip.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author juan.yee
 */
@Slf4j
@RestController
@RequestMapping("/api/clip/user")
@AllArgsConstructor
public class UserController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<String>> getAllDistinctUsers() {
        return new ResponseEntity<>(paymentService.getAllDistinctUsers(), HttpStatus.OK);
    }
}
