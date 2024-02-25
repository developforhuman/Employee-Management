package com.idrak.emp;

import com.idrak.emp.dao.entity.*;
import com.idrak.emp.dao.repository.*;
import com.idrak.emp.model.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
@Component
public class EmployeeManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Create Users
        User user1 = new User();
        user1.setUsername("Tural");
        user1.setPassword(passwordEncoder.encode("123456"));
        User user2 = new User();
        user2.setUsername("Samir");
        user2.setPassword(passwordEncoder.encode("123456"));
        userRepository.saveAll(List.of(user1, user2));

        // Create Positions
        Position position1 = new Position();
        position1.setName("BackEnd");
        Position position2 = new Position();
        position2.setName("FrontEnd");
        positionRepository.saveAll(List.of(position1, position2));

        // Create Departments
        Department department = new Department();
        department.setName("Programming");
        department.getPositions().addAll(Set.of(position1, position2));
        departmentRepository.save(department);

        // Create Employees
        Employee employee1 = new Employee();
        employee1.setUser(user1);
        employee1.setDepartment(department);
        employee1.getEmployeePositions().add(position1);

        Employee employee2 = new Employee();
        employee2.setUser(user2);
        employee2.setDepartment(department);
        employee2.getEmployeePositions().addAll(Set.of(position1, position2));
        employeeRepository.saveAll(List.of(employee1, employee2));

        // Create Projects
        Project project = new Project();
        project.setName("Project 1");
        project.getEmployees().addAll(Set.of(employee1, employee2));
        projectRepository.save(project);

        // Create Tasks
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setStartDate(LocalDateTime.now());
        task1.setEndDate(LocalDateTime.now().plusDays(7));
        task1.setProject(project);
        task1.setEmployee(employee1);
        task1.setStatus(TaskStatus.NOT_STARTED);

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setStartDate(LocalDateTime.now());
        task2.setEndDate(LocalDateTime.now().plusDays(5));
        task2.setProject(project);
        task2.setEmployee(employee2);
        task2.setStatus(TaskStatus.NOT_STARTED);

        Task task3 = new Task();
        task3.setName("Task 3");
        task3.setStartDate(LocalDateTime.now());
        task3.setEndDate(LocalDateTime.now().plusDays(3));
        task3.setProject(project);
        task3.setEmployee(employee1);
        task3.setStatus(TaskStatus.DONE);

        Task task4 = new Task();
        task4.setName("Task 4");
        task4.setStartDate(LocalDateTime.now());
        task4.setEndDate(LocalDateTime.now().plusDays(2));
        task4.setProject(project);
        task4.setEmployee(employee1);
        task4.setStatus(TaskStatus.DONE);

        taskRepository.saveAll(List.of(task1, task2, task3, task4));

    }
}
