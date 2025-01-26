package com.englishaoe.lesson.database.entity.partnership;

import com.englishaoe.lesson.database.entity.Customer;
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
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "partner_type_id")
    private Long partnerTypeId;
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
    @Column(name = "is_approved")
    private Boolean isApproved;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_type_id", insertable = false, updatable = false)
    PartnerType partnerType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    Customer customer;
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PromocodeUsage> promocodeUsageList = new LinkedList<>();
    //TODO make ManyToMany List<Customers> who enter promocode
}
