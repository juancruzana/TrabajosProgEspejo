package com.api.apiRest.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;


@Entity
@Table(name = "estudiantes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String enrollment; // Matrícula

    @ManyToMany(mappedBy = "students")
    @EqualsAndHashCode.Exclude
    private Set<Course> courses;
}