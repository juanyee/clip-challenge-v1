package com.example.clip.controller;

import com.example.clip.dto.ReportResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Retrieves the report list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The resources were retrieved successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please check required parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource requested is not available",
                    content = @Content) })
    public ResponseEntity<List<ReportResponseDTO>> getReport() {
        return new ResponseEntity<>(reportService.getReport(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retrieves the report associated with the userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The resources were retrieved successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please check required parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource requested is not available",
                    content = @Content) })
    public ResponseEntity<ReportResponseDTO> getReportByUserId(@PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new ClipBadRequestException("userId must not be null");
        }
        return new ResponseEntity<>(reportService.getReportByUserId(userId), HttpStatus.OK);
    }
}
