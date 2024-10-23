package com.englishaoe.lesson.dto.lesson;

import com.englishaoe.lesson.database.entity.variants.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    String taskContent;
    int taskType;
}
