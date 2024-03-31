package com.sqa.banking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sqa.banking.models.Customer;
import com.sqa.banking.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer getCustomerDetail(Long id) {
        return customerRepository.findById(id).get();
    }

    @Transactional(rollbackFor = { Exception.class })
    public Customer createCustomer(Customer request) {
        return customerRepository.save(request);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> searchCustomer(String name) {
        return customerRepository.findByName(name);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).get();
    }
}
