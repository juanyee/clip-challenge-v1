package com.example.clip.controller;

import com.example.clip.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    public void testGetAllDistinctUsers() {
        List<String> reportResponse = List.of("User_1", "User_2", "User_3");

        when(userService.getAllDistinctUsers()).thenReturn(reportResponse);

        final ResponseEntity<?> result = userController.getAllDistinctUsers();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(reportResponse, result.getBody());
    }
}