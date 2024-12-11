package com.englishaoe.lesson.database.entity.results;

public enum TaskResultTypeEnum {
    EXPERT("expert"),
    EXPRESS("express");

    private final String taskResultType;

    TaskResultTypeEnum(String taskResultType) {
        this.taskResultType = taskResultType;
    }

    public String getTaskResultType() {
        return taskResultType;
    }
}
