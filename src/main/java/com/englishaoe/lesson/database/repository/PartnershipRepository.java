package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.partnership.Partner;
import com.englishaoe.lesson.database.entity.partnership.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    @Query("SELECT p.partner FROM Partnership p WHERE p.promocode=:promocode")
    Partner findPartnerByPromocode(String promocode);

    Partnership findPartnershipByPartnerId(Long partnerId);
}
