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
    @Column(name = "partner_id")
    private Long partnerId;
    @Column(name = "discount")
    private Double discount;
    @Column(name = "partner_rate")
    private Double partnerRate;
    @Column(name = "contract_date")
    private String contractDate;
    @Column(name = "promocode")
    private String promocode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", insertable = false, updatable = false)
    Partner partner;
}
