package com.englishaoe.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Class contains all data needed for user login, despite
 *  the fact is that Student or Expert
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;

}
