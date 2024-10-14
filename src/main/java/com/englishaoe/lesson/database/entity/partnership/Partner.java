package com.englishaoe.lesson.database.entity.partnership;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partner")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "partnership_id")
    private Partnership partnership;
    @Column(name = "partner_name")
    private String partnerName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "inn")
    private String INN;
    @Column(name = "kpp")
    private String KPP;
    @Column(name = "bik")
    private String BIK;
    @Column(name = "rs")
    private String RS;
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Promocode> promocodeList = new LinkedList<>();
}
