package com.englishaoe.lesson.database.repository;

import com.englishaoe.lesson.database.entity.results.CustomerTask;
import com.englishaoe.lesson.database.entity.variants.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerTaskRepository extends JpaRepository<CustomerTask, Long> {
    List<CustomerTask> findByExamId(Long examId);
    List<CustomerTask> findByTaskIdAndCustomerIdAndExamIdIsNull(Long taskId, Long customerId);
    List<CustomerTask> findByCustomerId(Long customerId);

    @Query("select distinct v from CustomerTask ct " +
            "join Task t on ct.taskId = t.id " +
            "join Variant v on t.variantId = v.id " +
            "where ct.customerId = :customerId")
    List<Variant> findUniqueVariantsByCustomerId(@Param("customerId") Long customerId);
    @Query("select ct from CustomerTask ct where ct.expressCheckStatus.status = :status")
    CustomerTask findCustomerTaskByStatus(@Param("status") String status);
    @Query("select ct from CustomerTask ct where ct.expressCheckStatus.status = :status order by ct.completeDate ASC limit 1")
    CustomerTask findOldestCustomerTaskWithStatus(@Param("status") String status);
}
