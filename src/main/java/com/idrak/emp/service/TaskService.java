package com.idrak.emp.service;

import com.idrak.emp.model.enums.TaskStatus;
import com.idrak.emp.model.response.PageResponse;
import com.idrak.emp.model.response.UserTaskResponse;
import java.util.List;

public interface TaskService {
    List<UserTaskResponse> getUserTasks();

    PageResponse<UserTaskResponse> getUserTasksByUsernameAndTaskStatus(String username, TaskStatus taskStatus,
                                                                       int page, int size);

}
