package com.englishaoe.lesson.taskcheck.checkers;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.PromptBuilder;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.utility.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO taskText, topic 1..4, answer
@Service("second")
public class SecondTaskChecker implements TaskChecker, PromptBuilder {
    @Autowired
    private TaskTypeService taskTypeService;
    @Async
    @Override
    public void checkTask(CustomerTaskDTO customerTaskDTO) {
        String prompt = build(customerTaskDTO);
        //use an AI
    }
    public String build(CustomerTaskDTO customerTaskDTO) {
        String prompt = taskTypeService.getPromptByType(customerTaskDTO.getTask().getTaskType());
        List<String> topics = customerTaskDTO.getTask().getTaskContentDTO().getTopics();
        return String.format(prompt,
                customerTaskDTO.getTask().getTaskContentDTO().getTaskText(),
                topics.get(0),
                topics.get(1),
                topics.get(2),
                topics.get(3),
                customerTaskDTO.getTranscribateText());
    }
}
