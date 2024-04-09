package com.sqa.banking.models;


import java.util.Date;

import org.hibernate.annotations.GenericGenerator;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "loans")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GenericGenerator(name = "time_id", strategy = "com.sqa.banking.generators.TimestampGenerator")
    @GeneratedValue(generator = "time_id")
    private String id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "loan_term")
    private Integer loanTerm;
   
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "remaining")
    private Integer remaining;

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

    @Column(name = "owed")
    private Integer owed;
    
}
