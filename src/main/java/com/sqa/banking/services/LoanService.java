package com.sqa.banking.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sqa.banking.models.Loan;
import com.sqa.banking.repositories.LoanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository repository;

    public Loan getDetail(String id) {
        return repository.findById(id).get();
    }

    @Transactional(rollbackFor = {Exception.class})
    public Loan create(Loan request) {
        Date startDate = request.getStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, request.getLoanTerm());
        Date endDate = calendar.getTime();
        request.setEndDate(endDate);
        return repository.save(request);
    }

    public List<Loan> getAllActiveLoan(Long customerId) {
        return repository.getAllActiveLoan(customerId);
    }

    @Transactional(rollbackFor = { Exception.class })
    public Loan disbursalLoan(String id) {
        Loan loan = repository.getReferenceById(id);
        loan.setStatus(2);
        return repository.saveAndFlush(loan);
    }

    @Transactional(rollbackFor = { Exception.class })
    public Loan endLoan(String id) {
        Loan loan = repository.getReferenceById(id);
        loan.setStatus(3);
        return repository.saveAndFlush(loan);
    }

    @Transactional(rollbackFor = { Exception.class })
    public boolean payLoan(Loan loan, Integer money) {
        loan.setAmount(loan.getAmount() - money);
        repository.saveAndFlush(loan);
        return true;
    }
}
