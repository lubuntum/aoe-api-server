package com.englishaoe.lesson.taskcheck.checkers;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.ResultCollect;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.utility.textdistance.DistanceToGradeConverter;
import com.englishaoe.lesson.utility.textdistance.TextDistanceFactoryMethod;
import com.englishaoe.lesson.utility.textdistance.TextDistanceMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("first")
public class FirTaskChecker implements TaskChecker {
    @Autowired
    TextDistanceFactoryMethod textDistanceFactoryMethod;
    @Autowired
    ResultCollect resultCollect;
    //compare transcribe text and original for distance
    @Override
    public TaskResult checkTask(CustomerTaskDTO customerTaskDTO) {
        TextDistanceMethod textDistanceMethod = textDistanceFactoryMethod.getTextDistanceMethod(customerTaskDTO.getTextDistanceMethod());
        double distance = textDistanceMethod.compare(customerTaskDTO.getTask().getTaskContentDTO().getTaskText().get(0), customerTaskDTO.getTranscribateText());
        CheckDTO checkDTO = new CheckDTO();
        checkDTO.setGrade(DistanceToGradeConverter.convert(distance));
        return resultCollect.collect(customerTaskDTO, checkDTO);
    }
}
