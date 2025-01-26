package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.partnership.PartnerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnerTypeRepository extends JpaRepository<PartnerType, Long> {
    @Query("SELECT pT.id FROM PartnerType pT WHERE pT.type=:type")
    Long findPartnerTypeIdByType(String type);
}
