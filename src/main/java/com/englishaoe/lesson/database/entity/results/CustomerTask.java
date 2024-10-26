package com.englishaoe.lesson.database.entity.results;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_task")
public class CustomerTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long customerId;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "answer")
    private String answer;
    @Column(name = "complete_date")
    private String completeDate;
    @Column(name = "audio_path")
    private String audioPath;
}
