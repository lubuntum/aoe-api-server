package com.englishaoe.lesson.database.entity.variants;

public enum TaskTypeEnum {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);
    private final Integer taskType;

    TaskTypeEnum(Integer taskType) {
        this.taskType = taskType;
    }
    public Integer getTaskType(){
        return taskType;
    }
}
