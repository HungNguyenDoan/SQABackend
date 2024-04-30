package com.sqa.banking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.banking.models.Loan;
import com.sqa.banking.services.LoanService;
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

@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetLoanDetailsWhenValidIdThenReturnLoanDetails() throws Exception {
        String validId = "1";
        Loan loan = Loan.builder()
                .id(validId)
                .customerId(1L)
                .amount(10000)
                .interestRate(5.0)
                .startDate(new Date())
                .endDate(new Date())
                .build();
        given(loanService.getLoanDetail(validId)).willReturn(loan);

        mockMvc.perform(get("/loan/{id}", validId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(loan.getId()));
    }

    @Test
    public void testCreateLoanWhenValidRequestThenCreateLoan() throws Exception {
        Loan loanRequest = Loan.builder()
                .customerId(1L)
                .amount(10000)
                .interestRate(5.0)
                .startDate(new Date())
                .endDate(new Date())
                .build();
        Loan createdLoan = Loan.builder()
                .id("2")
                .customerId(loanRequest.getCustomerId())
                .amount(loanRequest.getAmount())
                .interestRate(loanRequest.getInterestRate())
                .startDate(loanRequest.getStartDate())
                .endDate(loanRequest.getEndDate())
                .build();
        given(loanService.createLoan(ArgumentMatchers.any(Loan.class))).willReturn(createdLoan);

        mockMvc.perform(post("/loan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(createdLoan.getId()));
    }

    @Test
    public void testSearchLoanWhenValidCriteriaThenReturnSearchResults() throws Exception {
        String searchCriteria = "John";
        Loan loan = Loan.builder()
                .id("1")
                .customerId(1L)
                .amount(10000)
                .build();
        given(loanService.searchLoans(searchCriteria)).willReturn(Collections.singletonList(loan));

        mockMvc.perform(get("/loan/search")
                        .param("criteria", searchCriteria))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(loan.getId()));
    }
}