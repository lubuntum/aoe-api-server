package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.partnership.PromocodeUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromocodeUsageRepository extends JpaRepository <PromocodeUsage, Long> {
    @Query("SELECT COUNT(pu) FROM PromocodeUsage pu WHERE pu.partnerId = :partnerId")
    Integer countByPartnerId(Long partnerId);
}
