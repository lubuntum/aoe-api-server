package com.englishaoe.lesson.database.entity.results;

import lombok.ToString;

public enum CheckStatusEnum {
    CHECKING("checking"),
    FREE("free"),
    COMPLETED("completed"),
    INCOMPLETE("incomplete"),
    UNTRANSCRIBED("untranscribed"),
    TRANSCRIBED("transcribed"),
    INSUFFICIENT("insufficient");


    private final String status;
    CheckStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
