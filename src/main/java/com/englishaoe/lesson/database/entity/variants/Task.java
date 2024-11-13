package com.englishaoe.lesson.database.entity.variants;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "task_type_id", insertable = false, updatable = false)
    private Long taskTypeId;
    @Column(name = "variant_id")
    private Long variantId;
    @Column(name = "task_content")
    private String taskContent;

    @ManyToOne()
    @JoinColumn(name = "task_type_id")
    private TaskType taskType;
}
