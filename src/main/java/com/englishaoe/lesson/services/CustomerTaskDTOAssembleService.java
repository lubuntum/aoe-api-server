package com.englishaoe.lesson.services;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.services.VariantService;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTaskDTOAssembleService {
    public static final String TRANSCRIBE_SERVICE_DEFAULT = "assemblyai";
    public static final String AI_SERVICE_DEFAULT = "vsegpt";
    public static final String AI_MODEL_DEFAULT = "openai/gpt-4o-latest";
    public static final String TEXT_DISTANCE_METHOD = "levenshtein";
    @Autowired
    private VariantService variantService;
    public CustomerTaskDTO assemble(CustomerTask cT){
        //if customer task was already setting for some parameters
        if (cT.getTempCheckingData() != null)
            return new Gson().fromJson(cT.getTempCheckingData(), CustomerTaskDTO.class);

        Task task = variantService.getTaskById(cT.getTaskId());

        TaskDTO taskDTO = new TaskDTO(task.getId(), task.getTaskContent(), task.getTaskType().getType());
        CustomerTaskDTO customerTaskDTO = new CustomerTaskDTO(
                cT.getId(),
                cT.getExamId(),
                taskDTO,
                cT.getAudioPath(),
                TRANSCRIBE_SERVICE_DEFAULT,
                AI_SERVICE_DEFAULT,
                AI_MODEL_DEFAULT,
                TEXT_DISTANCE_METHOD,
                null);
        return customerTaskDTO;
    }
}
