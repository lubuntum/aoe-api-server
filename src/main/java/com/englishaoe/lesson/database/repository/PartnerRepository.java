package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.dto.partner.PartnerProposalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    @Query("SELECT new com.englishaoe.lesson.dto.partner.PartnerProposalDTO(" +
            "p.id, p.customerId, pT.type, p.partnerName, p.phoneNumber, p.isApproved, " +
            "c.email, c.name, c.secondName, c.registrationDate, p.revenue) FROM Partner p " +
            "LEFT JOIN p.partnerType pT " +
            "LEFT JOIN p.customer c " +
            "WHERE p.isApproved=:isApproved")
    List<PartnerProposalDTO> findPartnersByApproving(Boolean isApproved);
    @Query("SELECT new com.englishaoe.lesson.dto.partner.PartnerProposalDTO(" +
            "p.id, p.customerId, pT.type, p.partnerName, p.phoneNumber, p.isApproved, " +
            "c.email, c.name, c.secondName, c.registrationDate, p.revenue) FROM Partner p " +
            "LEFT JOIN p.partnerType pT " +
            "LEFT JOIN p.customer c")
    List<PartnerProposalDTO> findAllPartners();
}
