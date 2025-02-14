package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.lesson.variant.VariantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.variant.VariantDTO(v.id, v.theme, v.imagePath, v.creationDate, v.isVisible) " +
            "FROM Variant v WHERE v.isVisible=:isVisible")
    List<VariantDTO> findAllVariantsAvailable(Pageable pageable, Boolean isVisible);
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.variant.VariantDTO(v.id, v.theme, v.imagePath, v.creationDate, v.isVisible) " +
            "FROM Variant v WHERE v.isVisible=:isVisible")
    Page<VariantDTO> findVariantsAvailableByPage(Pageable pageable, Boolean isVisible);
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.variant.VariantDTO(v.id, v.theme, v.imagePath, v.creationDate, v.isVisible) " +
            "FROM Variant v")
    List<VariantDTO> findAllVariantsThemesData();
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.variant.VariantDTO(v.id, v.theme, v.imagePath, v.creationDate, v.isVisible) " +
            "FROM Variant v WHERE v.isVisible = true")
    List<VariantDTO> findVisibleVariants();
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.TaskDTO(t.id, t.taskContent, tt.type) " +
            "FROM Task t JOIN t.taskType tt " +
            "WHERE t.variantId = :variantId")
    List<TaskDTO> findTasksByVariantId(@Param("variantId") Long variantId);
    @Query("SELECT COUNT(v) FROM Variant v where isVisible=true")
    Integer countVisibleVariants();


}
