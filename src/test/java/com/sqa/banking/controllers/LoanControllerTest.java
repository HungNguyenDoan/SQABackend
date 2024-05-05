package com.sqa.banking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.banking.models.Loan;
import com.sqa.banking.payload.request.LoanRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoanControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private EntityManager entityManager;

        @Autowired
        private ObjectMapper objectMapper;

        private Loan savedLoan;

        @BeforeEach
        void setUp() {
                // Setup test data
                Loan newLoan = Loan.builder()
                                .customerId(1L)
                                .amount(10000)
                                .interestRate(5.0)
                                .startDate(new Date())
                                .endDate(new Date())
                                .build();
                entityManager.persist(newLoan); // Persist the new loan
                entityManager.flush(); // Flush changes to ensure they're applied to the database

                // If you need to ensure the entity is in a clean state from the database, you
                // can re-fetch it
                savedLoan = entityManager.find(Loan.class, newLoan.getId());
        }

        @Test
        public void testGetLoanDetailsWhenValidIdThenReturnLoanDetails() throws Exception {
                mockMvc.perform(get("/loan/details/{loanId}", savedLoan.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.id").value(savedLoan.getId()));
        }

        @Test
        public void testCreateLoanWhenValidRequestThenCreateLoan() throws Exception {
                LoanRequest loanRequest = LoanRequest.builder()
                                .customer_id(2L)
                                .amount(20000)
                                .loan_term(12)
                                .has_collateral(1)
                                .has_salary_statement(1)
                                .has_salary_table(1)
                                .build();
                mockMvc.perform(post("/loan/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loanRequest)))
                                .andExpect(status().isOk())
                                // Assuming the create endpoint returns the created loan ID or object
                                .andExpect(jsonPath("$.data.customerId").value(loanRequest.getCustomer_id()))
                                .andExpect(jsonPath("$.data.amount").value(loanRequest.getAmount()));
        }
}