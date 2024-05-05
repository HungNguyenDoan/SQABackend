package com.sqa.banking.services;

import com.sqa.banking.models.Customer;
import com.sqa.banking.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private List<Customer> customerList;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFullName("John Doe");
        customerList = Arrays.asList(customer);
    }

    @Test
    public void testGetCustomerDetailWhenValidIdThenReturnCustomer() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomerDetail(customerId);
        assertEquals(customer, result);
    }

    @Test
    public void testCreateCustomerWhenValidCustomerThenReturnCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer result = customerService.createCustomer(customer);
        assertEquals(customer, result);
    }

    @Test
    public void testFindAllThenReturnListOfCustomers() {
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> result = customerService.findAll();
        assertEquals(customerList, result);
    }

    @Test
    public void testSearchCustomerWhenValidNameThenReturnListOfCustomers() {
        String name = "John";
        when(customerRepository.findByName(name)).thenReturn(customerList);
        List<Customer> result = customerService.searchCustomer(name);
        assertEquals(customerList, result);
    }

    @Test
    public void testGetCustomerWhenValidIdThenReturnCustomer() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomer(customerId);
        assertEquals(customer, result);
    }
}
