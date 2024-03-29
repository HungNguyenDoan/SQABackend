package com.sqa.banking.payload.request;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanRequest {
    @NotNull(message = "")
    Long customer_id;
    @NotNull(message = "")
    Date start_date;
    @NotNull(message = "")
    Integer loan_term;
    @NotNull(message = "")
    Integer amount;
    @NotNull(message = "")
    Double interest_rate;
    Integer has_salary_table;
    Integer has_salary_statement;
    Integer has_collateral; 
}
