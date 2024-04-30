package com.sqa.banking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.banking.models.Payment;
import com.sqa.banking.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePayment() throws Exception {
        Payment paymentRequest = new Payment(/* initialize with test data */);
        Payment createdPayment = new Payment(/* initialize with response data */);

        given(paymentService.createPayment(any(Payment.class))).willReturn(createdPayment);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testGetPaymentDetails() throws Exception {
        Long paymentId = 1L;
        Payment payment = new Payment(/* initialize with test data */);

        given(paymentService.getPaymentDetails(paymentId)).willReturn(payment);

        mockMvc.perform(get("/payments/{id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(payment.getId()));
    }

    @Test
    public void testListPaymentsForLoan() throws Exception {
        String loanId = "loan123";
        List<Payment> payments = Arrays.asList(new Payment(/* initialize with test data */), new Payment(/* and more test data */));

        given(paymentService.listPaymentsForLoan(loanId)).willReturn(payments);

        mockMvc.perform(get("/payments/loan/{loanId}", loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(payments.size()));
    }
}