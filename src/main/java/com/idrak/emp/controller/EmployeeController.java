package com.idrak.emp.controller;

import com.idrak.emp.model.request.EmployeeSearchRequest;
import com.idrak.emp.model.response.EmployeeTaskResponse;
import com.idrak.emp.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/search")
    public ResponseEntity<List<EmployeeTaskResponse>> getEmployeeTaskList(
            @RequestBody @Valid EmployeeSearchRequest request) {
        return ResponseEntity.ok(employeeService.findEmployeesWithTasksBetweenDatesAndDepartmentName(request));
    }

}

