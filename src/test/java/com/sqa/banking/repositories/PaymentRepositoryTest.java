package com.sqa.banking.repositories;

import com.sqa.banking.models.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(PaymentRepository.class)
public class PaymentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void whenGetAllLoanPayment_thenReturnPayments() {
        // given
        Payment payment1 = new Payment();
        payment1.setLoanId("loan123");
        payment1.setAmount(1000);
        payment1.setPay_date(new Date());
        // Set other properties as needed
        entityManager.persist(payment1);
        entityManager.flush();

        // when
        List<Payment> foundPayments = paymentRepository.getAllLoanPayment("loan123");

        // then
        assertThat(foundPayments).hasSize(1);
        assertThat(foundPayments.get(0).getLoanId()).isEqualTo("loan123");
    }

    @Test
    public void whenGetAllLoanPaymentWithNonExistingLoanId_thenReturnEmptyList() {
        // given
        String nonExistingLoanId = "nonExistingLoanId";

        // when
        List<Payment> foundPayments = paymentRepository.getAllLoanPayment(nonExistingLoanId);

        // then
        assertThat(foundPayments).isEmpty();
    }

    // Additional tests can be added here to cover other scenarios and methods
}