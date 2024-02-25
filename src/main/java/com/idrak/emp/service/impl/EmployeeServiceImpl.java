package com.idrak.emp.service.impl;

import com.idrak.emp.dao.entity.Employee;
import com.idrak.emp.dao.entity.Position;
import com.idrak.emp.dao.entity.Task;
import com.idrak.emp.dao.repository.EmployeeRepository;
import com.idrak.emp.model.dto.ProjectDto;
import com.idrak.emp.model.dto.TaskDto;
import com.idrak.emp.model.request.EmployeeSearchRequest;
import com.idrak.emp.model.response.EmployeeTaskResponse;
import com.idrak.emp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeTaskResponse> findEmployeesWithTasksBetweenDatesAndDepartmentName(
            EmployeeSearchRequest request) {
        List<Employee> employees = employeeRepository.findEmployeesWithTasksBetweenDatesAndDepartmentName(
                LocalDateTime.of(request.getStartDate(), LocalTime.of(0, 0, 0)),
                LocalDateTime.of(request.getEndDate(), LocalTime.of(23, 59, 59)),
                request.getDepartmentName());
        return buildResponse(request, employees);
    }

    private List<EmployeeTaskResponse> buildResponse(EmployeeSearchRequest request, List<Employee> employees) {
        return employees.stream().map(employee -> EmployeeTaskResponse.builder()
                .employeeId(employee.getId())
                .username(employee.getUser().getUsername())
                .tasks(employee.getTasks().stream()
                        .filter(task -> isValidDateInterval(request, task))
                        .map(task -> TaskDto.builder().taskStatus(task.getStatus())
                                .startDate(task.getStartDate())
                                .endDate(task.getEndDate())
                                .createdAt(task.getCreatedAt())
                                .name(task.getName()).build()).collect(Collectors.toList()))
                .projects(employee.getProjects().stream().map(project -> ProjectDto.builder()
                        .projectName(project.getName()).build()).collect(Collectors.toList()))
                .positionNames(employee.getEmployeePositions().stream().map(Position::getName)
                        .collect(Collectors.toList()))
                .build()
        ).collect(Collectors.toList());
    }

    private boolean isValidDateInterval(EmployeeSearchRequest request, Task task) {
        return (task.getStartDate().toLocalDate().isEqual(request.getStartDate()) ||
                task.getStartDate().toLocalDate().isAfter(request.getStartDate())) &&
                (task.getEndDate().toLocalDate().isEqual(request.getEndDate()) ||
                        task.getEndDate().toLocalDate().isBefore(request.getEndDate()));
    }

}
