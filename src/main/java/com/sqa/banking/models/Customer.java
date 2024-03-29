package com.sqa.banking.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "identify")
    private String identify;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "job")
    private String job;

    @Column(name = "nationality")
    private String nationality;
}
