package com.example.clip.controller;

import com.example.clip.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/clip/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Retrieves the list of users that have a payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The resources were retrieved successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please check required parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource requested is not available",
                    content = @Content) })
    public ResponseEntity<List<String>> getAllDistinctUsers() {
        return new ResponseEntity<>(userService.getAllDistinctUsers(), HttpStatus.OK);
    }
}
