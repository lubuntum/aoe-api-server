package com.englishaoe.lesson.taskcheck.checkers;

import com.englishaoe.lesson.database.entity.results.CheckStatusEnum;
import com.englishaoe.lesson.database.entity.results.TaskResult;
import com.englishaoe.lesson.database.entity.results.TaskResultTypeEnum;
import com.englishaoe.lesson.database.services.CustomerTaskService;
import com.englishaoe.lesson.database.services.ExamService;
import com.englishaoe.lesson.database.services.TaskResultService;
import com.englishaoe.lesson.dto.results.CheckDTO;
import com.englishaoe.lesson.dto.results.CustomerTaskDTO;
import com.englishaoe.lesson.services.transactions.PartnerRevenueTransactionalServices;
import com.englishaoe.lesson.taskcheck.ResultCollect;
import com.englishaoe.lesson.taskcheck.TaskCheckEndHandler;
import com.englishaoe.lesson.taskcheck.TaskChecker;
import com.englishaoe.lesson.utility.TextUtil;
import com.englishaoe.lesson.textdistance.DistanceToGradeConverter;
import com.englishaoe.lesson.textdistance.TextDistanceFactoryMethod;
import com.englishaoe.lesson.textdistance.TextDistanceMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("first")
public class FirTaskChecker implements TaskChecker {
    @Autowired
    TextDistanceFactoryMethod textDistanceFactoryMethod;
    @Autowired
    ResultCollect resultCollect;
    @Autowired
    TaskResultService taskResultService;
    @Autowired
    CustomerTaskService customerTaskService;
    @Autowired
    TaskCheckEndHandler taskCheckEndHandler;
    //compare transcribe text and original for distance
    @Transactional
    @Async
    @Override
    public void checkTask(CustomerTaskDTO customerTaskDTO) {
        try {
            TextDistanceMethod textDistanceMethod = textDistanceFactoryMethod.getService(customerTaskDTO.getTextDistanceMethod());
            double distance = textDistanceMethod
                    .compare(TextUtil.getClearText(customerTaskDTO.getTask().getTaskContentDTO().getTaskText().get(0))
                            ,TextUtil.getClearText(customerTaskDTO.getTranscribateText()));
            CheckDTO checkDTO = CheckDTO.createDefault();
            checkDTO.setGrade(DistanceToGradeConverter.convert(distance));
            taskResultService.saveTaskResult(resultCollect.collect(customerTaskDTO, checkDTO));

            taskCheckEndHandler.completeChecking(customerTaskDTO, checkDTO);

            customerTaskService.updateTempCheckingData(customerTaskDTO.getId(), null);
        } catch (Exception e) {
            taskCheckEndHandler.failTaskCheck(customerTaskDTO);
        }

        //return resultCollect.collect(customerTaskDTO, checkDTO);
    }
}
