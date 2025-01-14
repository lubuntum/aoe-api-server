package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.variants.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {
    @Query("SELECT t.prompt FROM TaskType t WHERE t.type = :type")
    String findPromptByType(@Param("type") int type);
    @Query("SELECT t FROM TaskType t where t.prompt IS NOT NULL")
    List<TaskType> findAllTaskType();
    TaskType findByType(Integer type);
    @Query("SELECT t.price FROM TaskType t WHERE t.type = :type")
    BigDecimal findPriceByType(Integer type);
    @Query("SELECT SUM(t.price) FROM TaskType t")
    BigDecimal findTotalPrice();
}
