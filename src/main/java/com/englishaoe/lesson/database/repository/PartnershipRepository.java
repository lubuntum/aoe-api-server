package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.database.entity.partnership.Partnership;
import com.englishaoe.lesson.dto.partnership.PartnershipDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    @Query("SELECT p.partner FROM Partnership p WHERE p.promocode=:promocode")
    Partner findPartnerByPromocode(String promocode);
    @Query("SELECT p FROM Partnership p WHERE p.promocode=:promocode")
    Partnership findPartnershipByPromocode(String promocode);

    @Query("SELECT new com.englishaoe.lesson.dto.partnership.PartnershipDTO( " +
            "p.id, p.partnerId, p.discount, p.partnerRate, p.contractDate, p.promocode) FROM Partnership p " +
            "WHERE p.partnerId = :partnerId")
    List<PartnershipDTO> findPartnershipsByPartnerId(Long partnerId);
}
