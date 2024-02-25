package com.idrak.emp.dao.entity;

import javax.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="department_id", nullable=false)
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "employee_positions",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private Set<Position> employeePositions = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<Task> tasks = new HashSet<>();

}
