package com.idrak.emp.dao.repository;

import com.idrak.emp.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT DISTINCT e FROM Employee e JOIN e.department d JOIN e.tasks t WHERE t.startDate >= :startDate " +
            "AND t.endDate <= :endDate AND d.name = :departmentName ")
    List<Employee> findEmployeesWithTasksBetweenDatesAndDepartmentName(@Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate,
                                                      @Param("departmentName") String departmentName);

}
