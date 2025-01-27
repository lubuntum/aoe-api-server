package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.database.entity.partnership.PartnerTypeEnum;
import com.englishaoe.lesson.database.repository.PartnerRepository;
import com.englishaoe.lesson.dto.partner.PartnerProposalDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PartnerService {
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    PartnerTypeService partnerTypeService;

    public void createNotApprovedPartnerForCustomerAccount(Long customerId){
        Partner partner = new Partner();
        partner.setCustomerId(customerId);
        partner.setPartnerTypeId(partnerTypeService.getPartnerTypeIdByType(PartnerTypeEnum.INDIVIDUAL.getType()));
        partner.setRevenue(new BigDecimal("0.0"));
        partner.setIsApproved(false);
        partnerRepository.save(partner);
    }
    public List<PartnerProposalDTO> getPartnersByApproving(Boolean isApproved) {
        return partnerRepository.findPartnersByApproving(isApproved);
    }
    public List<PartnerProposalDTO> getAllPartnersDTO(){
        return partnerRepository.findAllPartners();
    }
    public void approvePartner(Long partnerId){
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(
                ()-> new RegularException("Partner not found", HttpStatus.FORBIDDEN.value()));
        partner.setIsApproved(true);
        partnerRepository.save(partner);
    }

    public void deletePartnerById(Long partnerId){
        partnerRepository.deleteById(partnerId);
    }
}
