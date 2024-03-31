package com.sqa.banking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sqa.banking.models.Payment;
import com.sqa.banking.repositories.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    @Transactional(rollbackFor = { Exception.class })
    public Payment createPayment(Payment request) {
        return repository.save(request);
    }

    public List<Payment> getAllLoanPayment(String loanId) {
        return repository.getAllLoanPayment(loanId);
    }
}
