package com.englishaoe.lesson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LessonApplication {

	public static void main(String[] args) {
		SpringApplication.run(LessonApplication.class, args);
	}

}
