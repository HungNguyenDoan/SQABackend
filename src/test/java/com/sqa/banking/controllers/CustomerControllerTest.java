package com.sqa.banking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.banking.models.Customer;
import com.sqa.banking.payload.request.CustomerRequest;
import com.sqa.banking.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer savedCustomer;

    @BeforeEach
    void setUp() {
        // Setup test data
        Customer customer = Customer.builder()
                .fullName("John Doe")
                .phoneNumber("0123456789")
                .gender(1)
                .dob(new Date())
                .identify("123456789")
                .build();
        savedCustomer = customerRepository.save(customer);
    }

    @Test
    public void testDetailsWhenValidIdThenReturnCustomerDetails() throws Exception {
        mockMvc.perform(get("/customer/{id}", savedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName").value(savedCustomer.getFullName()));
    }

    @Test
    public void testCreateWhenValidRequestThenCreateCustomer() throws Exception {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("Jane Doe")
                .phone_number("0359745537")
                .gender(1)
                .dob(new Date())
                .identify("001202015256")
                .email("jane.doe@example.com")
                .city("CityName")
                .province("ProvinceName")
                .district("DistrictName")
                .address("123 Main St")
                .currentAddress("123 Main St, Apt 4")
                .job("Software Engineer")
                .build();

        mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName").value(customerRequest.getName()));
    }

    @Test
    public void testSearchCustomerWhenValidKeyThenReturnSearchResults() throws Exception {
        String searchKey = "John";
        mockMvc.perform(get("/customer/search")
                .param("key", searchKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fullName").value(savedCustomer.getFullName()));
    }
}