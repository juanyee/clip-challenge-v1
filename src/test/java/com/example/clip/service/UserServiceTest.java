package com.example.clip.service;

import com.example.clip.repository.PaymentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.PersistenceException;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author juan.yee
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private PaymentRepository paymentRepository;

    @Test(expected = PersistenceException.class)
    public void testGetAllDistinctUsersWithPersistenceIssuesReturnsPersistenceException() {
        when(paymentRepository.findAllDistinctUsers())
                .thenThrow(new PersistenceException("persistence exception"));

        userService.getAllDistinctUsers();
    }

    @Test
    public void testGetAllDistinctUsersReturnsSuccessfulResponse() {
        List<String> usersResponse = List.of("User_1", "User_2", "User_3");
        when(paymentRepository.findAllDistinctUsers()).thenReturn(usersResponse);

        final List<String> result = userService.getAllDistinctUsers();
        Assert.assertEquals(usersResponse, result);
    }
}