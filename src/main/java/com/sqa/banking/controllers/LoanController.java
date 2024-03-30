package com.sqa.banking.controllers;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @GetMapping("/all/{id}")
    public ResponseEntity<Object> getAllUserLoan(@PathVariable Long id) {
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách khoản vay thành công")
                .data(loanService.getAllActiveLoan(id))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createLoan(@RequestBody @Valid LoanRequest request) {
        Loan newLoan = Loan.builder()
                .customerId(request.getCustomer_id())
                .amount(request.getAmount())
                .remaining(request.getAmount())
                .interestRate(0.03)
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
