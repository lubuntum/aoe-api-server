package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.variants.Task;
import com.englishaoe.lesson.database.entity.variants.Variant;
import com.englishaoe.lesson.dto.lesson.TaskDTO;
import com.englishaoe.lesson.dto.lesson.VariantThemeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.VariantThemeDTO(v.theme) " +
            "FROM Variant v")
    List<VariantThemeDTO> findAllVariantsThemesData();
    @Query("SELECT new com.englishaoe.lesson.dto.lesson.TaskDTO(t.taskContent, tt.type) " +
            "FROM Task t JOIN t.taskType tt " +
            "WHERE t.variant.id = :variantId")
    List<TaskDTO> findTasksByVariantId(@Param("variantId") Long variantId);
}
