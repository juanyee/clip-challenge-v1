package com.example.clip.controller;

import com.example.clip.dto.DisbursementResponseDTO;
import com.example.clip.exception.ClipBadRequestException;
import com.example.clip.service.DisbursementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Retrieves the disbursement transaction list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The resources were retrieved successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please check required parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource requested is not available",
                    content = @Content) })
    public ResponseEntity<List<DisbursementResponseDTO>> getDisbursement() {
        return new ResponseEntity<>(disbursementService.getDisbursement(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieves the disbursement transaction associated with the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The resources were retrieved successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please check required parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource requested is not available",
                    content = @Content) })
    public ResponseEntity<DisbursementResponseDTO> getDisbursementById(@PathVariable Long id) {
        if (id == null) {
            throw new ClipBadRequestException("id must not be null");
        }
        return new ResponseEntity<>(disbursementService.getDisbursementById(id), HttpStatus.OK);
    }
}
