package com.example.clip.controller;

import com.example.clip.dto.DisbursementResponseDTO;
import com.example.clip.service.DisbursementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author juan.yee
 */
@Slf4j
@RestController
@RequestMapping("/api/clip/disbursements")
@AllArgsConstructor
public class DisbursementController {

    private final DisbursementService disbursementService;

    @GetMapping
    public ResponseEntity<List<DisbursementResponseDTO>> getDisbursement() {
        return new ResponseEntity<>(disbursementService.getDisbursement(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisbursementResponseDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(disbursementService.getDisbursementById(id), HttpStatus.OK);
    }
}
