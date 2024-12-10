package com.englishaoe.lesson.taskcheck;

import com.englishaoe.lesson.database.repository.TaskTypeRepository;
import com.englishaoe.lesson.database.services.TaskTypeService;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PromptBuilder {
    String build(CustomerTaskDTO customerTaskDTO);
}
