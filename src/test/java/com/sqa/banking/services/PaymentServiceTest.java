package com.sqa.banking.services;

import com.sqa.banking.models.Payment;
import com.sqa.banking.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PaymentServiceTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        // Create a payment that is past due
        Payment pastDuePayment = new Payment();
        pastDuePayment.setId(1L); // ID might be set automatically if using JPA
        pastDuePayment.setPay_date(getPastDate());
        pastDuePayment.setStatus(0); // Assuming 0 is the initial status

        // Create a payment that is not past due
        Payment onTimePayment = new Payment();
        onTimePayment.setId(2L); // ID might be set automatically if using JPA
        onTimePayment.setPay_date(getFutureDate());
        onTimePayment.setStatus(0); // Assuming 0 is the initial status

        payments = Arrays.asList(pastDuePayment, onTimePayment);

        // Save payments to the in-memory database
        paymentRepository.saveAll(payments);
    }

    @Test
    void testConfigStatus() {
        // Execute the method under test
        paymentService.configStatus(payments);

        // Fetch updated payments from the database
        Payment updatedPastDuePayment = paymentRepository.findById(payments.get(0).getId()).get();
        Payment updatedOnTimePayment = paymentRepository.findById(payments.get(1).getId()).get();

        // Assert that the status of the past due payment is updated
        assertEquals(1, updatedPastDuePayment.getStatus(), "The status of the past due payment should be updated.");

        // Assert that the status of the on-time payment remains unchanged
        assertEquals(0, updatedOnTimePayment.getStatus(), "The status of the on-time payment should remain unchanged.");
    }

    private Date getPastDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -10); // 10 days in the past
        return cal.getTime();
    }

    private Date getFutureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 10); // 10 days in the future
        return cal.getTime();
    }
}