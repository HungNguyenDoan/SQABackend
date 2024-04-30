package com.sqa.banking.repositories;

import com.sqa.banking.models.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void whenFindByCustomerId_thenReturnLoans() {
        // given
        Loan loan = new Loan();
        loan.setId("1");
        loan.setCustomerId(1L);
        loan.setAmount(10000);
        loan.setInterestRate(5.0);
        loan.setLoanTerm(12);
        loan.setStartDate(new Date());
        loan.setEndDate(new Date());
        loan.setStatus(1); // Assuming 1 signifies an active loan
        entityManager.persist(loan);
        entityManager.flush();

        // when
        List<Loan> foundLoans = loanRepository.findByCustomerId(loan.getCustomerId());

        // then
        assertFalse(foundLoans.isEmpty());
        assertEquals(loan.getCustomerId(), foundLoans.get(0).getCustomerId());
    }

    @Test
    public void whenInvalidCustomerId_thenReturnEmpty() {
        // given
        Long invalidCustomerId = -99L;

        // when
        List<Loan> foundLoans = loanRepository.findByCustomerId(invalidCustomerId);

        // then
        assertTrue(foundLoans.isEmpty());
    }

    @Test
    public void whenCreateLoanWithExistingId_thenThrowException() {
        // given
        Loan loan1 = new Loan();
        loan1.setId("1");
        entityManager.persist(loan1);
        entityManager.flush();

        Loan loan2 = new Loan();
        loan2.setId("1");

        // then
        assertThrows(DataIntegrityViolationException.class, () -> {
            entityManager.persistAndFlush(loan2);
        });
    }

    // Add more tests as needed for other repository methods
}