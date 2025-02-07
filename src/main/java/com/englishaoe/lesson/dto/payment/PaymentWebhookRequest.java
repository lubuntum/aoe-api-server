package com.englishaoe.lesson.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentWebhookRequest {
    private String event;
    private PaymentData data;
    @Data
    public static class PaymentData {
        private String id;
        private String status;
        private Amount amount;
        private String currency;

        @Data
        public static class Amount {
            private String value; // Amount value
            private String currency; // Currency code
        }
    }
}
