package com.sqa.banking.services;

import com.sqa.banking.models.Loan;
import com.sqa.banking.repositories.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private Loan mockLoan;

    @BeforeEach
    void setUp() {
        mockLoan = Loan.builder()
                .id("1")
                .customerId(123L)
                .amount(10000)
                .interestRate(5.0)
                .loanTerm(12)
                .status(1)
                .build();
    }

    @Test
    void testGetDetailWhenValidIdThenReturnLoan() {
        when(loanRepository.findById(anyString())).thenReturn(Optional.of(mockLoan));
        Loan result = loanService.getDetail("1");
        assertEquals(mockLoan, result);
    }

    @Test
    void testGetDetailWhenInvalidIdThenThrowException() {
        when(loanRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> loanService.getDetail("invalid-id"));
    }

    @Test
    void testCreate() {
        when(loanRepository.saveAndFlush(any(Loan.class))).thenReturn(mockLoan);
        Loan result = loanService.create(mockLoan);
        assertEquals(mockLoan, result);
    }

    @Test
    void testGetAllActiveLoan() {
        when(loanRepository.getAllActiveLoan(anyLong())).thenReturn(Collections.singletonList(mockLoan));
        List<Loan> result = loanService.getAllActiveLoan(123L);
        assertEquals(Collections.singletonList(mockLoan), result);
    }

    @Test
    @Transactional
    void testDisbursalLoan() {
        when(loanRepository.getReferenceById(anyString())).thenReturn(mockLoan);
        when(loanRepository.saveAndFlush(any(Loan.class))).thenReturn(mockLoan);
        Loan result = loanService.disbursalLoan("1");
        assertEquals(2, result.getStatus());
        assertNotNull(result.getStartDate());
        assertNotNull(result.getEndDate());
    }

    @Test
    @Transactional
    void testEndLoan() {
        when(loanRepository.getReferenceById(anyString())).thenReturn(mockLoan);
        when(loanRepository.saveAndFlush(any(Loan.class))).thenReturn(mockLoan);
        Loan result = loanService.endLoan("1");
        assertEquals(3, result.getStatus());
    }

    @Test
    @Transactional
    void testPayLoan() {
        when(loanRepository.getReferenceById(anyString())).thenReturn(mockLoan);
        when(loanRepository.saveAndFlush(any(Loan.class))).thenReturn(mockLoan);
        boolean result = loanService.payLoan("1", 5000);
        assertTrue(result);
        // Additional assertions can be added here to verify the remaining amount
        // after the payment logic is implemented in the payLoanLate method.
    }
}
