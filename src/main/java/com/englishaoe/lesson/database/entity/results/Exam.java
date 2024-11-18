package com.englishaoe.lesson.database.entity.results;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "variant_id")
    private Long variantId;
    @Column(name = "user_id")
    private Long customerId;
    @Column(name = "exam_complete_date")
    private String examCompleteDate;
    @Column(name = "express_send_date")
    private String expressSendDate;
    @Column(name = "expert_send_date")
    private String expertSendDate;
    @Column(name = "express_total_grade")
    private Integer expressTotalGrade;
    @Column(name = "expert_total_grade")
    private Integer expertTotalGrade;

}
