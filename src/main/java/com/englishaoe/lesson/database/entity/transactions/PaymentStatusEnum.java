package com.englishaoe.lesson.database.entity.transactions;

public enum PaymentStatusEnum {
    PENDING("pending"),
    SUCCEEDED("succeeded"),
    CANCELED("canceled");

    private final String status;

    PaymentStatusEnum(String status) {
        this.status = status;
    }
    public String getStatus(){
        return status;
    }
}
