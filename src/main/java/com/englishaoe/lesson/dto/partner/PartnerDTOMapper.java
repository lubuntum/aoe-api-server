package com.englishaoe.lesson.dto.partner;

import com.englishaoe.lesson.database.entity.partnership.Partner;

public class PartnerDTOMapper {
    public static PartnerDTO toDTO (Partner partner, Integer promocodeUsageCount) {
        return new PartnerDTO(partner.getId(),
                partner.getPartnerType().getType(),
                partner.getPartnerName(),
                partner.getPhoneNumber(),
                partner.getRevenue(),
                partner.getINN(),
                partner.getKPP(),
                partner.getBIK(),
                partner.getRS(),
                promocodeUsageCount);
    }
}
