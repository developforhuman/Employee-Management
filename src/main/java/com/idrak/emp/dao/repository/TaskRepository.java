package com.idrak.emp.dao.repository;

import com.idrak.emp.dao.entity.Task;
import com.idrak.emp.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByEmployeeUserUsernameAndStatus(String username, TaskStatus taskStatus, Pageable pageable);

}
