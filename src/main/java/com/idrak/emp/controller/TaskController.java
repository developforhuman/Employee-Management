package com.idrak.emp.controller;

import com.idrak.emp.model.enums.TaskStatus;
import com.idrak.emp.model.response.PageResponse;
import com.idrak.emp.model.response.UserTaskResponse;
import com.idrak.emp.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/list")
    public List<UserTaskResponse> getTaskList() {
        return taskService.getUserTasks();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<UserTaskResponse>> getUserTaskList(
            @RequestParam String username,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size) {
        return ResponseEntity.ok(taskService.getUserTasksByUsernameAndTaskStatus(username, status, page, size));
    }

}

