package com.englishaoe.lesson.database.entity.partnership;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partnership")
public class Partnership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "discount")
    private String discount;
    @Column(name = "partner_rate")
    private String partnerRate;
    @Column(name = "contract_date")
    private String contractDate;
}
