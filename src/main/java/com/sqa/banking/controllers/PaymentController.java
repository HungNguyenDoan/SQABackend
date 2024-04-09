package com.sqa.banking.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.banking.models.Payment;
import com.sqa.banking.payload.request.PaymentRequest;
import com.sqa.banking.payload.response.ErrorResponse;
import com.sqa.banking.payload.response.SuccessResponse;
import com.sqa.banking.services.LoanService;
import com.sqa.banking.services.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    private final LoanService loanService;

    @PostMapping("/pay")
    public ResponseEntity<Object> payLoan(@RequestBody @Valid PaymentRequest request) {
        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .loanId(request.getLoan_id())
                .pay_date(new Date())
                .build();
        if (loanService.payLoan(payment.getLoanId(), payment.getAmount())) {
            SuccessResponse response = SuccessResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Thanh toán thành công")
                    .data(paymentService.createPayment(payment))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra thử lại sau"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/history/{loanId}")
    public ResponseEntity<Object> getPaymentHistory(@PathVariable String loanId) {
        paymentService.configStatus(paymentService.getAllLoanPayment(loanId));
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy lịch sử thanh toán thành công")
                .data(paymentService.getAllLoanPayment(loanId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
