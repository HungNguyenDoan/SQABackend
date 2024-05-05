package com.sqa.banking.services;

import com.sqa.banking.models.Loan;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private EntityManager entityManager;

    private Loan createTestLoan() {
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
        entityManager.persist(newLoan);
        entityManager.flush();
        return newLoan;
    }

    @Test
    void testGetDetailWhenValidIdThenReturnLoan() {
        // Arrange: Create and persist a Loan entity
        Loan newLoan = Loan.builder()
                .customerId(123L)
                .amount(10000)
                .interestRate(5.0)
                .loanTerm(12)
                .status(1)
                .startDate(new Date()) // Assuming your Loan entity requires a start date
                .build();
        entityManager.persist(newLoan);
        entityManager.flush(); // Ensure the entity is persisted before querying
        entityManager.clear(); // Clear the persistence context to ensure retrieval from the database

        // Act: Retrieve the loan by id
        Loan result = loanService.getDetail(newLoan.getId());

        // Assert: Verify the retrieved loan matches the persisted one
        assertNotNull(result);
        assertEquals(newLoan.getId(), result.getId());
        assertEquals(newLoan.getCustomerId(), result.getCustomerId());
        assertEquals(newLoan.getAmount(), result.getAmount());
        // Add more assertions as needed to verify the Loan details
    }

    @Test
    void testGetDetailWhenInvalidIdThenThrowException() {
        assertThrows(Exception.class, () -> loanService.getDetail("invalid-id"));
    }

    @Test
    void testGetAllActiveLoan() {
        Loan mockLoan = createTestLoan();
        List<Loan> result = loanService.getAllActiveLoan(mockLoan.getCustomerId());
        assertFalse(result.isEmpty());
        assertEquals(mockLoan.getCustomerId(), result.get(0).getCustomerId());
    }

    @Test
    void testDisbursalLoan() {
        Loan mockLoan = createTestLoan();
        Loan result = loanService.disbursalLoan(mockLoan.getId());
        assertEquals(2, result.getStatus());
        assertNotNull(result.getStartDate());
        assertNotNull(result.getEndDate());
    }

    @Test
    void testEndLoan() {
        Loan mockLoan = createTestLoan();
        mockLoan.setStatus(2); // Assuming the loan is in a disbursed state
        entityManager.persist(mockLoan);
        Loan result = loanService.endLoan(mockLoan.getId());
        assertEquals(3, result.getStatus());
    }

    @Test
    void testPayLoan() {
        Loan mockLoan = createTestLoan();
        entityManager.persist(mockLoan);
        boolean result = loanService.payLoan(mockLoan.getId(), 5000);
        assertTrue(result);
        Loan updatedLoan = entityManager.find(Loan.class, mockLoan.getId());
        assertEquals(68000, updatedLoan.getRemaining()); // This assertion depends on your payLoan implementation
    }

    @Test
    void testCreate() {
        Loan newLoan = Loan.builder()
                .customerId(123L)
                .amount(10000)
                .interestRate(5.0)
                .loanTerm(12)
                .status(1)
                .build();

        Loan savedLoan = loanService.create(newLoan);
        Loan foundLoan = entityManager.find(Loan.class, savedLoan.getId());

        assertNotNull(foundLoan);
        assertEquals(newLoan.getCustomerId(), foundLoan.getCustomerId());
        assertEquals(newLoan.getAmount(), foundLoan.getAmount());
    }
}
