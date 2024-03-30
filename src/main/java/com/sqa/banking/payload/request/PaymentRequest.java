package com.sqa.banking.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    String loan_id;
    @NotNull(message = "")
    private Integer amount;
}
