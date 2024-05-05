package com.sqa.banking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sqa.banking.models.Loan;

public interface LoanRepository extends JpaRepository<Loan, String> {
    @Query("select l from Loan l where l.customerId = :customerId")
    List<Loan> getAllActiveLoan(@Param("customerId") Long id);

    List<Loan> findByCustomerId(Long customerId);
}
