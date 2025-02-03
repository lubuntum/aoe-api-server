package com.englishaoe.lesson.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerProposalDTO {
    private Long id;
    private Long customerId;
    private String type;
    private String partnerName;
    private String phoneNumber;
    private Boolean isApproved;
    private String email;
    private String name;
    private String secondName;
    private String patronymic;
    private String registrationDate;
    private BigDecimal revenue;
    private String INN;
    private String KPP;
    private String BIK;
    private String RS;
}
