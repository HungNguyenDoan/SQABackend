package com.sqa.banking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sqa.banking.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("Select p from Payment p where p.loanId = :loanId")
    List<Payment> getAllLoanPayment(@Param("loanId") String loanId);
}
