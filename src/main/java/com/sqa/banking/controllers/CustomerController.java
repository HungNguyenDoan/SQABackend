package com.sqa.banking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sqa.banking.models.Customer;
import com.sqa.banking.payload.request.CustomerRequest;
import com.sqa.banking.payload.response.SuccessResponse;
import com.sqa.banking.services.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> details(@PathVariable Long id) {
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Lấy thông tin khách hàng thành công")
                .data(customerService.getCustomerDetail(id))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid CustomerRequest request) {
        Customer newCustomer = Customer.builder()
                .fullName(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .currentAddress(request.getCurrentAddress())
                .district(request.getDistrict())
                .dob(request.getDob())
                .email(request.getEmail())
                .gender(request.getGender())
                .job(request.getJob())
                .identify(request.getIdentify())
                .phoneNumber(request.getPhone_number())
                .province(request.getProvince())
                .nationality(request.getNationality())
                .build();
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Tạo mới khách hàng thành công")
                .data(customerService.createCustomer(newCustomer))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchCustomer(@RequestParam String key) {
        SuccessResponse response = SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message("searched successfully")
                .data(customerService.searchCustomer(key))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
