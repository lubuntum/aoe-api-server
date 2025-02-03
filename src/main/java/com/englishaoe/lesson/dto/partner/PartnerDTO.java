package com.englishaoe.lesson.dto.partner;

import com.englishaoe.lesson.database.entity.partnership.Partnership;
import com.englishaoe.lesson.dto.partnership.PartnershipDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDTO {
    private Long id;
    private String type;
    private String partnerName;
    private String partnerNumber;
    private BigDecimal revenue;
    private String INN;
    private String KPP;
    private String BIK;
    private String RS;
    private Integer promocodeUsageCount;
    private List<PartnershipDTO> partnerships;
}
