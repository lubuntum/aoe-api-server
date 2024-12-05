package com.englishaoe.lesson.dto.lesson;

import com.englishaoe.lesson.database.entity.variants.TaskType;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String taskContent;

    private TaskContentDTO taskContentDTO;
    private int taskType;

    public TaskDTO(Long id, String taskContent, int taskType) {
        this.id = id;
        this.taskContent = taskContent;
        this.taskType = taskType;
    }

    public void parseTaskContent() {
        Gson gson = new Gson();
        taskContentDTO = gson.fromJson(taskContent, TaskContentDTO.class);
    }

}
