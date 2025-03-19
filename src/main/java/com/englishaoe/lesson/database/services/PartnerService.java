package com.englishaoe.lesson.database.services;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.database.entity.partnership.PartnerType;
import com.englishaoe.lesson.database.entity.partnership.PartnerTypeEnum;
import com.englishaoe.lesson.database.repository.PartnerRepository;
import com.englishaoe.lesson.database.repository.PartnershipRepository;
import com.englishaoe.lesson.dto.account.CustomerRegistrationDTO;
import com.englishaoe.lesson.dto.partner.PartnerAccountDTO;
import com.englishaoe.lesson.dto.partner.PartnerDTO;
import com.englishaoe.lesson.dto.partner.PartnerDTOMapper;
import com.englishaoe.lesson.dto.partner.PartnerProposalDTO;
import com.englishaoe.lesson.exceptions.RegularException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    PartnerTypeService partnerTypeService;
    @Autowired
    PromocodeUsageService promocodeUsageService;
    @Autowired
    PartnershipRepository partnershipRepository;

    public Long getAllApprovedPartnerCount(){
        return partnerRepository.findApprovedPartnersCount();
    }

    public void createNotApprovedPartnerForCustomerAccount(CustomerRegistrationDTO accountData){
        Partner partner = new Partner();
        partner.setCustomerId(accountData.getCustomerId());
        partner.setPhoneNumber(accountData.getPhoneNumber());
        partner.setPartnerTypeId(partnerTypeService.getPartnerTypeIdByType(PartnerTypeEnum.INDIVIDUAL.getType()));
        partner.setRevenue(new BigDecimal("0.0"));
        partner.setIsApproved(false);
        partnerRepository.save(partner);
    }
    /**
     * Some sum will be subs from revenue because it was paid
     */
    public Boolean subAmountFromPartnerRevenueById(Long partnerId, BigDecimal amount) {
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(
                ()->new RegularException("No partner found", HttpStatus.FORBIDDEN.value()));
        BigDecimal revenue = Optional.ofNullable(partner.getRevenue()).orElse(BigDecimal.ZERO);
        if (revenue.subtract(amount).compareTo(BigDecimal.ZERO) < 0)
            throw new RegularException("Partner doesn't have enough revenue", HttpStatus.FORBIDDEN.value());
        partner.setRevenue(revenue.subtract(amount));
        partnerRepository.save(partner);
        return true;
    }
    public Boolean addAmountToPartnerRevenueById(Long partnerId, BigDecimal amount) {
        PartnerDTO partnerDTO = partnerRepository.findPartnerDTOById(partnerId);
        BigDecimal revenue = Optional.ofNullable(partnerDTO.getRevenue()).orElse(BigDecimal.ZERO);
        partnerDTO.setRevenue(revenue.add(amount));
        partnerRepository.updatePartnerRevenue(partnerDTO.getId(), partnerDTO.getRevenue());
        return true;
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
    @Transactional
    public PartnerDTO getPartnerDTOById(Long partnerId){
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(
                ()->new RegularException("Cannot find partner with provided id", HttpStatus.FORBIDDEN.value()));
        Integer promocodeUsageCount = promocodeUsageService.getPromocodeUsageCountByPartnerId(partnerId);
        return PartnerDTOMapper.toDTO(partner, promocodeUsageCount);
    }
    public boolean updatePartnerData(PartnerAccountDTO partnerAccountDTO) {
        //TODO get DTO class instead not partner
        Partner partner = partnerRepository.findById(partnerAccountDTO.getId()).orElseThrow(
                ()-> new RegularException("Partner not found", HttpStatus.FAILED_DEPENDENCY.value()));
        Long partnerTypeId = partnerTypeService.getPartnerTypeIdByType(partnerAccountDTO.getType());
        if (partnerTypeId == null)
            throw new RegularException("Partner type not found", HttpStatus.FORBIDDEN.value());
        partner.setPartnerTypeId(partnerTypeId);
        partner.setPartnerName(partnerAccountDTO.getPartnerName());
        partner.setPhoneNumber(partnerAccountDTO.getPartnerNumber());
        partner.setINN(partnerAccountDTO.getINN());
        partner.setKPP(partnerAccountDTO.getKPP());
        partner.setBIK(partnerAccountDTO.getBIK());
        partner.setRS(partnerAccountDTO.getRS());
        partnerRepository.save(partner);
        return true;
    }
    public PartnerDTO getPartnerDTOByCustomerId(Long customerId) {
        PartnerDTO partnerDTO = partnerRepository.findPartnerDTOByCustomerId(customerId);
        partnerDTO.setPromocodeUsageCount(promocodeUsageService.getPromocodeUsageCountByPartnerId(partnerDTO.getId()));
        partnerDTO.setPartnerships(partnershipRepository.findPartnershipsByPartnerId(partnerDTO.getId()));
        return partnerDTO;
    }
    public PartnerAccountDTO getPartnerAccountDTOByCustomerId(Long customerId) {
        PartnerAccountDTO partnerAccountDTO = partnerRepository.findPartnerAccountDTOByCustomerId(customerId);
        partnerAccountDTO.setPromocodeUsageCount(promocodeUsageService
                .getPromocodeUsageCountByPartnerId(partnerAccountDTO.getId()));
        partnerAccountDTO.setPartnerships(partnershipRepository
                .findPartnershipsByPartnerId(partnerAccountDTO.getId()));
        return partnerAccountDTO;
    }
}
