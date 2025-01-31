package com.englishaoe.lesson.database.entity.partnership;

import com.englishaoe.lesson.database.entity.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promocode_usage")
public class PromocodeUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "partner_id")
    private Long partnerId;
    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne()
    @JoinColumn(name = "partner_id", insertable = false, updatable = false)
    private Partner partner;

    @Override
    public String toString() {
        return "PromocodeUsage{" +
                "id=" + id +
                ", partnerId=" + partnerId +
                ", customerId=" + customerId +
                ", partner=" + partner.getPartnerName() +
                '}';
    }
}
