package com.englishaoe.lesson.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Class contains all data needed for customer login, despite
 *  the fact is that Customer or Expert
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;

}