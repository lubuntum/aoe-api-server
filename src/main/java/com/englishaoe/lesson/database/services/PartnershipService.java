package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.database.entity.partnership.Partnership;
import com.englishaoe.lesson.database.entity.role.RoleEnum;
import com.englishaoe.lesson.database.repository.PartnerRepository;
import com.englishaoe.lesson.database.repository.PartnershipRepository;
import com.englishaoe.lesson.utility.DateUtil;
import com.englishaoe.lesson.utility.PromocodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartnershipService {
    @Autowired
    private PartnershipRepository partnershipRepository;
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private PartnerService partnerService;
    @Transactional
    public void createPartnershipForPartner(Partner partner, Double discount, Double partnerRate){
        Partnership partnership = new Partnership();
        partnership.setPartnerId(partner.getId());
        partnership.setDiscount(discount);
        partnership.setPartnerRate(partnerRate);
        partnership.setContractDate(DateUtil.getCurrentDate());
        partnership.setPromocode(PromocodeUtil.generatePromocode(10));
        partnershipRepository.save(partnership);
        customerServices.addRoleToCustomer(partner.getCustomerId(), RoleEnum.PARTNER.getRole());
        partnerService.approvePartner(partner.getId());
    }
}
