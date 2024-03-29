package com.sqa.banking.services;

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

    @Transactional(rollbackFor = {Exception.class})
    public Customer createCustomer(Customer request) {
        return customerRepository.save(request);
    }
}
