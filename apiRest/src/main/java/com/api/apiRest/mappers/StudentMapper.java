package com.api.apiRest.mappers;


import com.api.apiRest.dtos.request.StudentCreateDTO;
import com.api.apiRest.entity.Student;
import com.api.apiRest.dtos.response.StudentDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toDto(Student student);

    // Set de Entidades a Lista de DTOs para el CourseWithStudentsDTO
    List<StudentDTO> toDtoList(Set<Student> students);

    // Lista de Entidades a Lista de DTOs
    List<StudentDTO> toDtoList(List<Student> students);

    Student toEntity(StudentCreateDTO createDTO);
}