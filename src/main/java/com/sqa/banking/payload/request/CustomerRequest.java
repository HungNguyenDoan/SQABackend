package com.sqa.banking.payload.request;

import java.util.Date;

import com.sqa.banking.constraints.Unique;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CustomerRequest {
    @NotNull(message = "Tên không được để trống")
    @Unique(table = "Customer", fieldName = "fullName", message = "Tên khách hàng đã tồn tại trong hệ thống")
    String name;
    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(message = "Số điện thoại sai định dạng", regexp = "(03|05|07|08|09|01[2|6|8|9])([0-9]{8})")
    String phone_number;
    @NotNull(message = "Giới tính không được để trống")
    @Min(message = "Chọn đúng giới tính", value = 0)
    @Max(message = "Chọn đúng giới tính", value = 1)
    Integer gender;
    Date dob;
    @NotNull(message = "Định danh không được để trống")
    @Unique(table = "Customer", fieldName = "identify", message = "Đã có số định danh này trong hệ thống")
    @Max(value = 12, message = "Số định danh phải có 12 chữ số")
    @Min(value = 12, message = "Số định danh phải có 12 chữ số")
    String identify;
    @Pattern(message = "Email sai định dạng", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    String email;
    String city;
    String province;
    String district;
    String address;
    String currentAddress;
    String job;
    String nationality;
}
