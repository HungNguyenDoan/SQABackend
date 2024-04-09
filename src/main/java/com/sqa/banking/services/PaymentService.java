package com.sqa.banking.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sqa.banking.models.Loan;
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

    public void create(Loan loan) {
        double remainRoot = (double)loan.getAmount();
        double monthlyRoot = (double)((double)loan.getAmount()/(double)loan.getLoanTerm());
        Date startDate = loan.getStartDate();
        for(int i = 1; i <= loan.getLoanTerm(); i++){
            Payment payment = new Payment();
            long monthlyInterset = Math.round(remainRoot * loan.getInterestRate()/12);
            remainRoot = remainRoot - monthlyRoot;
            payment.setTenor(i);
            payment.setRemainRoot(Math.round(remainRoot));
            payment.setMonthlyRoot(Math.round(monthlyRoot));
            payment.setMonthlyInterest(monthlyInterset);
            payment.setMonthlyPayment(payment.getMonthlyInterest() + payment.getMonthlyRoot());
            payment.setStatus(0);
            payment.setLoanId(loan.getId());
            payment.setPay_date(createPayDate(startDate));
            startDate = createPayDate(startDate);
            repository.save(payment);
        }
    }
    public void configStatus(List<Payment> payments){
        Date currentDate = new Date();
        for(int i = 0; i < payments.size(); i++){
            if(payments.get(i).getPay_date().compareTo(currentDate) < 0){
                payments.get(i).setStatus(1);
            }
        }
        repository.saveAllAndFlush(payments);
        
    }


    private Date createPayDate(Date startDate) {
        // Khởi tạo lớp Calendar và đặt startDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Cộng thêm một tháng vào ngày startDate
        calendar.add(Calendar.MONTH, 1);

        // Lấy ngày sau khi đã cộng thêm một tháng
        Date payDate = calendar.getTime();

        return payDate;
    }



   
}
