package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.dto.partner.PartnerDTO;
import com.englishaoe.lesson.dto.partner.PartnerProposalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    @Query("SELECT new com.englishaoe.lesson.dto.partner.PartnerProposalDTO(" +
            "p.id, p.customerId, pT.type, p.partnerName, p.phoneNumber, p.isApproved, " +
            "c.email, c.name, c.secondName, c.patronymic, c.registrationDate, p.revenue, " +
            "p.INN, p.KPP, p.BIK, p.RS) FROM Partner p " +
            "LEFT JOIN p.partnerType pT " +
            "LEFT JOIN p.customer c " +
            "WHERE p.isApproved=:isApproved")
    List<PartnerProposalDTO> findPartnersByApproving(Boolean isApproved);
    @Query("SELECT new com.englishaoe.lesson.dto.partner.PartnerProposalDTO(" +
            "p.id, p.customerId, pT.type, p.partnerName, p.phoneNumber, p.isApproved, " +
            "c.email, c.name, c.secondName, c.patronymic, c.registrationDate, p.revenue, " +
            "p.INN, p.KPP, p.BIK, p.RS) FROM Partner p " +
            "LEFT JOIN p.partnerType pT " +
            "LEFT JOIN p.customer c")
    List<PartnerProposalDTO> findAllPartners();

    @Query("SELECT new com.englishaoe.lesson.dto.partner.PartnerDTO(" +
            "p.id, pT.type, p.partnerName, p.phoneNumber, p.revenue, " +
            "p.INN, p.KPP, p.BIK, p.RS, null, null) FROM Partner p " +
            "LEFT JOIN p.partnerType pT " +
            "WHERE p.id = :id")
    PartnerDTO findPartnerDTOById(Long id);

    @Query("SELECT new com.englishaoe.lesson.dto.partner.PartnerDTO(" +
            "p.id, pT.type, p.partnerName, p.phoneNumber, p.revenue, " +
            "p.INN, p.KPP, p.BIK, p.RS, null, null) FROM Partner p " +
            "LEFT JOIN p.partnerType pT " +
            "WHERE p.customerId = :customerId")
    PartnerDTO findPartnerDTOByCustomerId(Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE Partner p SET p.revenue = :revenue WHERE p.id = :id")
    void updatePartnerRevenue(Long id, BigDecimal revenue);


}
