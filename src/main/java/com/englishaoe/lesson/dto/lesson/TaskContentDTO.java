package com.englishaoe.lesson.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskContentDTO {
    private String taskGuide;
    private List<String> taskText;
    private List<String> topics;
    private List<String> questions;
    private List<String> subTasks;
}
