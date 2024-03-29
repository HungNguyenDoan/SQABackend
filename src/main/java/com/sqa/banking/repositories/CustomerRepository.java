package com.sqa.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sqa.banking.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
