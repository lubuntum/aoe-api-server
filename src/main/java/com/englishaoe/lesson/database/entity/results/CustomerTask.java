package com.englishaoe.lesson.database.entity.results;
import com.englishaoe.lesson.database.entity.variants.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Column(name = "express_check_status_id")
    private Long expressCheckStatusId;
    @Column(name = "expert_check_status_id")
    private Long expertCheckStatusId;

    @Column(name = "answer")
    private String answer;
    @Column(name = "complete_date")
    private String completeDate;
    @Column(name = "audio_path")
    private String audioPath;
    @Column(name = "temp_checking_data")
    private String tempCheckingData;
    @ManyToOne()
    @JoinColumn(name = "express_check_status_id", insertable = false, updatable = false)
    private CheckStatus expressCheckStatus;
    @ManyToOne()
    @JoinColumn(name = "expert_check_status_id", insertable = false, updatable = false)
    private CheckStatus expertCheckStatus;
    @ManyToOne()
    @JoinColumn(name = "task_id", insertable = false,  updatable = false)
    private Task task;
    @OneToMany(mappedBy = "customerTaskId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskResult> taskResults;
}
