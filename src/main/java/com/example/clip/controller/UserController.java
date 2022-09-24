package com.example.clip.controller;

import com.example.clip.service.UserService;
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
    public ResponseEntity<List<String>> getAllDistinctUsers() {
        return new ResponseEntity<>(userService.getAllDistinctUsers(), HttpStatus.OK);
    }
}
