package com.idrak.emp.model.response;

import com.idrak.emp.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskResponse {

    private Long id;

    private String taskName;

    private String employeeName;

    private TaskStatus taskStatus;

}
