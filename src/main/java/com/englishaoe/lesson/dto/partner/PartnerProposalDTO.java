package com.englishaoe.lesson.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private String secondName;
    private String registrationDate;
}
