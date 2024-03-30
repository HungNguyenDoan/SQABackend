package com.sqa.banking.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "loans")

@Data
@Builder
@AllArgsConstructor

public class Loan {
    @Id
    @GeneratedValue(generator = "timestampGenerator")
    private String id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "loan_term")
    private Integer loanTerm;

    @Column(name = "amount")
    Integer amount;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "has_salary_table")
    private Integer hasSalaryTable;

    @Column(name = "has_salary_statement")
    private Integer hasSalaryStatement;

    @Column(name = "has_collateral")
    private Integer hasCollateral;
}
