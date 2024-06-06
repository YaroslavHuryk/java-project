package com.practice.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.demo.entity.Customer;
import com.practice.demo.repository.CustomerRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerUnitTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testTakeAllCustomers() throws JsonProcessingException {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("newemail@gmail.com");

        List<Customer> customerList = Collections.singletonList(customer);
        given(customerRepo.findAll()).willReturn(customerList);

        String expectedJson = "[{\"id\":1,\"name\":\"John Doe\",\"email\":\"newemail@gmail.com\"}]";
        given(objectMapper.writeValueAsString(customerList)).willReturn(expectedJson);

        String resultJson = customerController.takeAllCustomers();
        assertEquals(expectedJson, resultJson);
    }

    @Test
    public void testAddCustomer() throws JsonProcessingException {
        Customer customer = new Customer();
        customer.setId(2);
        customer.setName("Yaroslav Huryk");
        customer.setEmail("someemail@gmail.com");

        customerController.addCustomer(customer);

        verify(customerRepo).save(customer);
    }

    @Test
    public void testTakeSomeCustomer() throws JsonProcessingException {
        Customer customer = new Customer();
        customer.setId(2);
        customer.setName("Yaroslav Huryk");
        customer.setEmail("someemail@gmail.com");

        when(customerRepo.findById(2)).thenReturn(java.util.Optional.of(customer));

        Customer result = customerController.getMethodName(2);
        assertEquals(customer, result);
        verify(customerRepo).findById(2);
    }

    @Test
    public void testRemoveCustomer() {
        Customer customer = new Customer();
        customer.setId(2);
        customer.setName("Yaroslav Huryk");
        customer.setEmail("someemail@gmail.com");

        customerController.removeCustomer(2);

        verify(customerRepo).deleteById(2);
    }

    @Test
    public void testChangeCustomer() throws JsonProcessingException {
        Customer inputCustomer = new Customer();
        inputCustomer.setId(1);
        inputCustomer.setName("John Doe");
        inputCustomer.setEmail("johndoe@example.com");

        when(customerRepo.existsById(1)).thenReturn(true);
        when(customerRepo.save(any(Customer.class))).thenReturn(inputCustomer);
        when(objectMapper.writeValueAsString(any(Customer.class)))
                .thenReturn("{\"id\":1,\"name\":\"John Doe\",\"email\":\"johndoe@example.com\"}");

        String result = customerController.changeCustomer(inputCustomer);

        verify(customerRepo).existsById(1);
        verify(customerRepo).save(any(Customer.class));
        verify(objectMapper).writeValueAsString(any(Customer.class));

        String expectedResult = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"johndoe@example.com\"}";
        assertEquals(expectedResult, result);
    }

}
