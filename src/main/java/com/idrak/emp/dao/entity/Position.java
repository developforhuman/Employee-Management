package com.idrak.emp.dao.entity;

import javax.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "employeePositions")
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(mappedBy = "positions")
    private Set<Department> departments = new HashSet<>();

}
