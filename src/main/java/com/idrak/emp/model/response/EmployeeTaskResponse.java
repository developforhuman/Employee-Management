package com.idrak.emp.model.response;

import com.idrak.emp.model.dto.ProjectDto;
import com.idrak.emp.model.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTaskResponse {

    private Long employeeId;

    private String username;

    private List<TaskDto> tasks;

    private List<ProjectDto> projects;

    private List<String> positionNames;

}
