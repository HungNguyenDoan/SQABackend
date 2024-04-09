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
@Table(name = "payments")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Integer amount;
    @Column(name = "tenor")
    private Integer tenor; //kì hạn

    @Column(name = "remain_root")
    private long remainRoot;//gốc còn lại

    @Column(name = "loan_id")
    private String loanId;

    @Column(name = "monthly_interest")
    private long monthlyInterest; //lãi suất tháng

    @Column(name = "monthly_root")
    private long monthlyRoot; // gốc hàng tháng

    @Column(name = "monthlyPayment")
    private long monthlyPayment; 
    
    @Column(name = "pay_date")
    private Date pay_date; //hạn thanh toán

    @Column(name = "status")
    private int status;

    

}
