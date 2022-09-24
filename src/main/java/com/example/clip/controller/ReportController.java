package com.example.clip.controller;

import com.example.clip.dto.ReportResponseDTO;
import com.example.clip.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author juan.yee
 */
@Slf4j
@RestController
@RequestMapping("/api/clip/reports")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{userId}")
    public ResponseEntity<ReportResponseDTO> getReportByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(reportService.getReportByUserId(userId), HttpStatus.OK);
    }
}
