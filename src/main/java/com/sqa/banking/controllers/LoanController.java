package com.sqa.banking.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.banking.models.Loan;
import com.sqa.banking.payload.request.LoanRequest;
import com.sqa.banking.payload.response.SuccessResponse;
import com.sqa.banking.services.LoanService;
import com.sqa.banking.services.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final PaymentService paymentService;
    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getAllUserLoan(@PathVariable Long id) {
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách khoản vay thành công")
                .data(loanService.getAllActiveLoan(id))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/details/{loanId}")
    public ResponseEntity<Object> getLoanDetails(@PathVariable String loanId) {
    // Lấy thông tin chi tiết của khoản vay với mã loanId
        Loan loan = loanService.getDetail(loanId);

    if (loan == null) {
        // Nếu không tìm thấy khoản vay, trả về mã lỗi NOT_FOUND
        return new ResponseEntity<>("Không tìm thấy thông tin khoản vay", HttpStatus.NOT_FOUND);
    }

    // Nếu tìm thấy, trả về thông tin chi tiết của khoản vay
    SuccessResponse response = SuccessResponse.builder()
            .status(HttpStatus.OK.value())
            .message("Lấy thông tin khoản vay thành công")
            .data(loan)
            .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
}
    @PostMapping("/create")
    public ResponseEntity<Object> createLoan(@RequestBody @Valid LoanRequest request) {
        Date currentDate = new Date();
        LocalDate testDate = LocalDate.of(2023, 12, 1);
        Date date = Date.from(testDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); //fake database, bao giờ test thì thay vào
        Loan newLoan = Loan.builder()
                .customerId(request.getCustomer_id())
                .amount(request.getAmount())
                .remaining(request.getAmount())
                .interestRate(0.065)
                .startDate(date)
                .loanTerm(request.getLoan_term())
                .hasCollateral(request.getHas_collateral())
                .hasSalaryStatement(request.getHas_salary_statement())
                .hasSalaryTable(request.getHas_salary_table())
                .status(0)
                .build();
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("")
                .data(loanService.create(newLoan))
                .build();
        paymentService.create(newLoan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
