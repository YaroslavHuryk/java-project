package com.practice.demo.controller;

import com.practice.demo.entity.Customer;
import com.practice.demo.repository.CustomerRepo;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerRepo customerRepo;
    private final ObjectMapper objectMapper;

    @PostMapping("/api/add")
    public void addCustomer(@RequestBody Customer customer) {
        customerRepo.save(customer);
    }

    @GetMapping("api/all")
    public String takeAllCustomers() {
        Iterable<Customer> customers = customerRepo.findAll();
        String resultJson = null;
        try {
            resultJson = objectMapper.writeValueAsString(customers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    @GetMapping("api/takesome")
    public Customer getMethodName(@RequestParam int id) {
        return customerRepo.findById(id).orElseThrow();
    }

    @DeleteMapping("api/delete")
    public void removeCustomer(@RequestParam int id) {
        customerRepo.deleteById(id);
    }

    @PutMapping("api/change")
    public String changeCustomer(@RequestBody Customer customer) {
        if (!customerRepo.existsById(customer.getId())) {
            return "There is no such row";
        }
        String result = null;
        try {
            result = objectMapper.writeValueAsString(customerRepo.save(customer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
