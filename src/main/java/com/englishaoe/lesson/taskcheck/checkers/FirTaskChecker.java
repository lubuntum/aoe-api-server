package com.englishaoe.lesson.taskcheck.checkers;

import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.taskcheck.ResultCollect;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.utility.TextUtil;
import com.englishaoe.lesson.textdistance.DistanceToGradeConverter;
import com.englishaoe.lesson.textdistance.TextDistanceFactoryMethod;
import com.englishaoe.lesson.textdistance.TextDistanceMethod;
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
        TextDistanceMethod textDistanceMethod = textDistanceFactoryMethod.getService(customerTaskDTO.getTextDistanceMethod());
        double distance = textDistanceMethod
                .compare(TextUtil.getClearText(customerTaskDTO.getTask().getTaskContentDTO().getTaskText().get(0))
                        ,TextUtil.getClearText(customerTaskDTO.getTranscribateText()));
        CheckDTO checkDTO = new CheckDTO();
        checkDTO.setGrade(DistanceToGradeConverter.convert(distance));
        return resultCollect.collect(customerTaskDTO, checkDTO);
    }
}
