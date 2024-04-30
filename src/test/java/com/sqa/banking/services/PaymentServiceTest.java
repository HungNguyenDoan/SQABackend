package com.sqa.banking.services;

import com.sqa.banking.models.Payment;
import com.sqa.banking.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        // Create a payment that is past due
        Payment pastDuePayment = new Payment();
        pastDuePayment.setId(1L);
        pastDuePayment.setPay_date(getPastDate());
        pastDuePayment.setStatus(0); // Assuming 0 is the initial status

        // Create a payment that is not past due
        Payment onTimePayment = new Payment();
        onTimePayment.setId(2L);
        onTimePayment.setPay_date(getFutureDate());
        onTimePayment.setStatus(0); // Assuming 0 is the initial status

        payments = Arrays.asList(pastDuePayment, onTimePayment);
    }

    @Test
    void testConfigStatus() {
        // Assume all payments are initially not updated in the repository
        doNothing().when(paymentRepository).saveAllAndFlush(anyList());

        // Execute the method under test
        paymentService.configStatus(payments);

        // Verify that saveAllAndFlush was called once with our payments list
        verify(paymentRepository, times(1)).saveAllAndFlush(payments);

        // Assert that the status of the past due payment is updated
        assertEquals(1, payments.get(0).getStatus(), "The status of the past due payment should be updated.");

        // Assert that the status of the on-time payment remains unchanged
        assertEquals(0, payments.get(1).getStatus(), "The status of the on-time payment should remain unchanged.");
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