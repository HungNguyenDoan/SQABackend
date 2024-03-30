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

    @Transactional(rollbackFor = { Exception.class })
    public Loan create(Loan request) {
        return repository.saveAndFlush(request);
    }

    public List<Loan> getAllActiveLoan(Long customerId) {
        return repository.getAllActiveLoan(customerId);
    }

    @Transactional(rollbackFor = { Exception.class })
    public Loan disbursalLoan(String id) {
        Loan loan = repository.getReferenceById(id);
        Date startDate = new Date();
        loan.setStartDate(startDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, loan.getLoanTerm());
        Date endDate = calendar.getTime();
        loan.setEndDate(endDate);
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
    public boolean payLoan(String loanId, Integer money) {
        Loan currentLoan = repository.getReferenceById(loanId);
        Integer interestMoney = roundMoney(
                currentLoan.getInterestRate() * currentLoan.getAmount() / currentLoan.getLoanTerm());
        Integer baseMoneyPerMonth = roundMoney((double) (currentLoan.getAmount() / currentLoan.getLoanTerm()));
        Integer returnMoneyThisMonth = interestMoney + baseMoneyPerMonth;
        if (returnMoneyThisMonth == money) {
            currentLoan.setRemaining(currentLoan.getRemaining() - baseMoneyPerMonth);
            repository.saveAndFlush(currentLoan);
            return true;
        }
        if (returnMoneyThisMonth < money) {
            Integer baseReturnMoney = money - interestMoney;
            currentLoan.setRemaining(currentLoan.getRemaining() - baseReturnMoney);
            repository.saveAndFlush(currentLoan);
            return true;
        }
        if (returnMoneyThisMonth > money) {
            Integer excessMoney = money - returnMoneyThisMonth;
            Integer baseMoneyRemaining = currentLoan.getRemaining() - roundMoney(excessMoney / 1.09);
            currentLoan.setRemaining(baseMoneyRemaining);
            repository.saveAndFlush(currentLoan);
        }
        return true;
    }

    public static Integer roundMoney(Double money) {
        return (int) (Math.round(money / 1000.0) * 1000.0);
    }
}
