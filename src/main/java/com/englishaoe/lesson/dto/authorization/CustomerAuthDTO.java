package com.englishaoe.lesson.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for grab customer auth (login) data
 * like username, password*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAuthDTO {
    private Long id;
    private String email;
    private String password;

}
