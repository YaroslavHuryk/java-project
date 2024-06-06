package com.practice.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.demo.entity.Customer;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Integer> {

}
