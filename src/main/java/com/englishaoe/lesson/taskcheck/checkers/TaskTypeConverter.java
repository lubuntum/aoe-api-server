package com.englishaoe.lesson.taskcheck.checkers;
/**
 * Helps convert taskType to some specific service name for checking
 * better somehow try to make this name constant
 * */
public enum TaskTypeConverter {
    FIRST(1, "first"),
    SECOND(2, "second"),
    THIRD(3, "third"),
    FOURTH(4, "fourth");

    private final int taskType;
    private final String taskCheckerServiceName;
    TaskTypeConverter(int taskType, String taskCheckerServiceName) {
        this.taskType = taskType;
        this.taskCheckerServiceName = taskCheckerServiceName;
    }

    public int getTaskType() {
        return taskType;
    }

    public String getTaskCheckerServiceName() {
        return taskCheckerServiceName;
    }
    public static String serviceNameFromTaskType(int taskType) throws IllegalAccessException {
        for(TaskTypeConverter t : TaskTypeConverter.values()) {
            if (t.getTaskType() == taskType) return t.getTaskCheckerServiceName();
        }
        throw new IllegalAccessException("Invalid task type " + taskType);
    }
}
