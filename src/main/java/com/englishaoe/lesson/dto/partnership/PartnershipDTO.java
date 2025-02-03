package com.englishaoe.lesson.dto.partnership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnershipDTO {
    private Long id;
    private Long partnerId;
    private Double discount;
    private Double partnerRate;
    private String contractDate;
    private String promocode;
}
