package com.sqa.banking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.banking.models.Customer;
import com.sqa.banking.payload.request.CustomerRequest;
import com.sqa.banking.payload.response.SuccessResponse;
import com.sqa.banking.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDetailsWhenValidIdThenReturnCustomerDetails() throws Exception {
        Long validId = 1L;
        Customer customer = Customer.builder()
                .id(validId)
                .fullName("John Doe")
                .build();
        given(customerService.getCustomerDetail(validId)).willReturn(customer);

        mockMvc.perform(get("/customer/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName").value(customer.getFullName()));
    }

    @Test
    public void testCreateWhenValidRequestThenCreateCustomer() throws Exception {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("Jane Doe")
                .phone_number("0123456789")
                .gender(1)
                .dob(new Date())
                .identify("123456789")
                .build();
        Customer createdCustomer = Customer.builder()
                .id(2L)
                .fullName(customerRequest.getName())
                .phoneNumber(customerRequest.getPhone_number())
                .gender(customerRequest.getGender())
                .dob(customerRequest.getDob())
                .identify(customerRequest.getIdentify())
                .build();
        given(customerService.createCustomer(ArgumentMatchers.any(Customer.class))).willReturn(createdCustomer);

        mockMvc.perform(post("/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName").value(createdCustomer.getFullName()));
    }

    @Test
    public void testSearchCustomerWhenValidKeyThenReturnSearchResults() throws Exception {
        String searchKey = "John";
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("John Doe")
                .build();
        given(customerService.searchCustomer(searchKey)).willReturn(Collections.singletonList(customer));

        mockMvc.perform(get("/customer/search")
                        .param("key", searchKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fullName").value(customer.getFullName()));
    }
}
