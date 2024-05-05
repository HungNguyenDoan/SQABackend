package com.sqa.banking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.banking.models.Loan;
import com.sqa.banking.models.Payment;
import com.sqa.banking.payload.request.PaymentRequest;
import com.sqa.banking.repositories.LoanRepository;
import com.sqa.banking.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LoanRepository loanRepository;

    private Payment savedPayment;

    private Loan loan;

    private String loanId;

    @BeforeEach
    void setUp() {
        // Initialize with test data
        Payment payment = new Payment();
        payment.setAmount(1000);
        payment.setLoanId("loan123");
        payment.setPay_date(new Date());
        // Add more fields as required
        savedPayment = paymentRepository.save(payment);

        Loan newLoan = Loan.builder()
                .customerId(1L)
                .amount(50000)
                .remaining(50000)
                .loanTerm(12)
                .startDate(new Date())
                .updateDate(new Date())
                .endDate(new Date())
                .interestRate(5.0)
                .build();
        loan = loanRepository.save(newLoan); // Save and get the persisted entity
        loanId = loan.getId();
    }

    @Test
    public void testCreatePayment() throws Exception {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .loan_id(loanId)
                .amount(20000)
                .build();

        mockMvc.perform(post("/payment/pay") // Ensure the URL matches your controller's endpoint
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk()) // Adjust based on your actual response status
                .andExpect(jsonPath("$.data.amount").value(paymentRequest.getAmount()));
    }

    @Test
    public void testGetPaymentDetails() throws Exception {
        mockMvc.perform(get("/payment/history/{loanId}", savedPayment.getLoanId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].loanId").value(savedPayment.getLoanId()));
    }

    @Test
    public void testListPaymentsForLoan() throws Exception {
        mockMvc.perform(get("/payment/history/{loanId}", savedPayment.getLoanId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].loanId").value(savedPayment.getLoanId()));
    }
}