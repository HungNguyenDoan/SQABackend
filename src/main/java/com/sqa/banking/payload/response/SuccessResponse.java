package com.sqa.banking.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    Integer status;
    String message;
    Object data;
}
