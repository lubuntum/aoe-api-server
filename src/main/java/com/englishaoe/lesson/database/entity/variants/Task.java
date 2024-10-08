package com.englishaoe.lesson.database.entity.variants;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskContent;
    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
    @ManyToOne
    @JoinColumn(name = "task_type_id")
    private TaskType taskType;
}
