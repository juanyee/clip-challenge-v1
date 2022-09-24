package com.example.clip.controller;

import com.example.clip.dto.ReportResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/api/clip/reports")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportResponseDTO>> getReport() {
        return new ResponseEntity<>(reportService.getReport(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReportResponseDTO> getReportByUserId(@PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new ClipBadRequestException("userId must not be null");
        }
        return new ResponseEntity<>(reportService.getReportByUserId(userId), HttpStatus.OK);
    }
}
