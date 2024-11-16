package com.englishaoe.lesson.database.entity.results;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_result")
public class TaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_task_id")
    private Long customerTaskId;
    @Column(name = "expert_id")
    private Long expertId;
    @Column(name = "task_result_type_id", insertable = false, updatable = false)
    private Long taskResultTypeId;
    @Column(name = "result")
    private String result;
    @Column(name = "send_date")
    private String sendDate;
    @ManyToOne()
    @JoinColumn(name = "task_result_type_id")
    private TaskResultType taskResultType;
}
