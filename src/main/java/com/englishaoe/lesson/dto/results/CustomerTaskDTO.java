package com.englishaoe.lesson.dto.results;

import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTaskDTO {
    //for database updating
    Long id;
    //for task content (create dynamic prompt)
    TaskDTO task;
    //for transcription
    String audioPath;
    String transcriptionServiceName;
    String aiServiceName;
    String aiModelName;
    String textDistanceMethod;
    //all text
    String transcribateText;
}
