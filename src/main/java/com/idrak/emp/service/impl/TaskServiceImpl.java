package com.idrak.emp.service.impl;

import com.idrak.emp.dao.entity.Task;
import com.idrak.emp.dao.repository.TaskRepository;
import com.idrak.emp.model.enums.TaskStatus;
import com.idrak.emp.model.request.EmployeeSearchRequest;
import com.idrak.emp.model.response.PageResponse;
import com.idrak.emp.model.response.TaskEmployeeResponse;
import com.idrak.emp.model.response.UserTaskResponse;
import com.idrak.emp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TaskEmployeeResponse> findEmployeesWithTasksBetweenDatesAndDepartmentName(
            EmployeeSearchRequest request) {
        List<Task> employees = taskRepository.findAllByDateIntervalAndDepartmentName(
                LocalDateTime.of(request.getStartDate(), LocalTime.of(0, 0, 0)),
                LocalDateTime.of(request.getEndDate(), LocalTime.of(23, 59, 59)),
                request.getDepartmentName());
        return buildResponse(employees);
    }

    private List<TaskEmployeeResponse> buildResponse(List<Task> tasks) {
        return tasks.stream().map(task -> TaskEmployeeResponse.builder()
                .employeeId(task.getEmployee().getId())
                .username(task.getEmployee().getUser().getUsername())
                .taskName(task.getName())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .createdAt(task.getCreatedAt())
                .taskStatus(task.getStatus())
                .projectName(task.getProject().getName())
                .build()
        ).collect(Collectors.toList());
    }

}
