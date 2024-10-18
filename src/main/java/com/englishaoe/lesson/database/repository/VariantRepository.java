package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.dto.lesson.VariantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.VariantDTO(v.theme) " +
            "FROM Variant v")
    List<VariantDTO> findAllVariants();
}
