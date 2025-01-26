package com.englishaoe.lesson.dto.partner;

import com.englishaoe.lesson.database.entity.partnership.Partner;

public class PartnerProposalDTOMapper {
    public static Partner parse(PartnerProposalDTO dto) {
        Partner partner = new Partner();
        partner.setId(dto.getId());
        partner.setCustomerId(dto.getCustomerId());
        partner.setIsApproved(dto.getIsApproved());
        return partner;
    }
}
