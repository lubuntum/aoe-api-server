package com.englishaoe.lesson.dto.results;

import com.englishaoe.lesson.utility.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckDTO {
    private int grade;
    private String checkDate;
    private String comment;
    public static CheckDTO createDefault() {
        return new CheckDTO(0, DateUtil.getCurrentDate(), null);
    }
}
