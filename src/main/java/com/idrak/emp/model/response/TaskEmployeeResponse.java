package com.idrak.emp.model.response;

import com.idrak.emp.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEmployeeResponse {

    private Long employeeId;

    private String username;

    private String taskName;

    private TaskStatus taskStatus;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createdAt;

    private String projectName;

}
