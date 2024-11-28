package com.englishaoe.lesson.dto.results;

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
    Long taskId;
    //for transcription
    String audioPath;
    String transcriptionServiceName;

}
