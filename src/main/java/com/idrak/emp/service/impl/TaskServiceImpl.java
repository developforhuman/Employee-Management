package com.idrak.emp.service.impl;

import com.idrak.emp.dao.entity.Task;
import com.idrak.emp.dao.repository.TaskRepository;
import com.idrak.emp.model.enums.TaskStatus;
import com.idrak.emp.model.response.PageResponse;
import com.idrak.emp.model.response.UserTaskResponse;
import com.idrak.emp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final String ID_COLUMN = "id";
    private final TaskRepository taskRepository;

    @Override
    public List<UserTaskResponse> getUserTasks() {
        List<Task> tasks = taskRepository.findAll();
        return buildUserTaskResponse(tasks);
    }

    @Override
    public PageResponse<UserTaskResponse> getUserTasksByUsernameAndTaskStatus(String username, TaskStatus taskStatus,
                                                                              int page, int size) {
        Page<Task> taskList;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(ID_COLUMN).descending());
        if (taskStatus != null) {
            taskList = taskRepository.findAllByEmployeeUserUsernameAndStatus(username, taskStatus, pageable);
        } else {
            taskList = taskRepository.findAllByEmployeeUserUsernameAndStatus(username, TaskStatus.DONE, pageable);
        }
        return PageResponse.<UserTaskResponse>builder()
                .pageSize(size)
                .currentPage(page)
                .itemsCount(taskList.getTotalElements())
                .pageCount(taskList.getTotalPages())
                .items(buildUserTaskResponse(taskList.getContent()))
                .build();
    }

    private static List<UserTaskResponse> buildUserTaskResponse(List<Task> taskList) {
        List<UserTaskResponse> userTaskList = new ArrayList<>();
        for (Task task : taskList) {
            userTaskList.add(UserTaskResponse.builder()
                    .id(task.getId())
                    .taskName(task.getName())
                    .taskStatus(task.getStatus())
                    .employeeName(task.getEmployee().getUser().getUsername())
                    .build());
        }
        return userTaskList;
    }

}
