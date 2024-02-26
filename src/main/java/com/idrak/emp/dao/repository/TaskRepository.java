package com.idrak.emp.dao.repository;

import com.idrak.emp.dao.entity.Task;
import com.idrak.emp.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByEmployeeUserUsernameAndStatus(String username, TaskStatus taskStatus, Pageable pageable);

    @Query("SELECT t FROM Task t JOIN t.employee e JOIN e.department d WHERE t.startDate >= :startDate" +
            " AND t.endDate <= :endDate AND d.name = :departmentName")
    List<Task> findAllByDateIntervalAndDepartmentName(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate,
                                                      @Param("departmentName") String departmentName);

}
