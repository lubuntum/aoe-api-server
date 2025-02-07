package com.englishaoe.lesson.dto.payment;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    @SerializedName("id")
    private String paymentId;
    private Amount amount;
    private String status;
    @SerializedName("created_at")
    private String createdAt;

}
